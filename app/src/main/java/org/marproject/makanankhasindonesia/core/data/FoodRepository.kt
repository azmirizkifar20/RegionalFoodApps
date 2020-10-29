package org.marproject.makanankhasindonesia.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.remote.RemoteDataSource
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.domain.repository.IFoodRepository
import org.marproject.makanankhasindonesia.core.utils.AppExecutors
import org.marproject.makanankhasindonesia.core.utils.DataMapper

class FoodRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllFood(): LiveData<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, List<FoodResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Food>> {
                return Transformations.map(localDataSource.getAllFood()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Food>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<FoodResponse>>> =
                remoteDataSource.getAllFood()

            override fun saveCallResult(data: List<FoodResponse>) {
                val foodList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertFood(foodList)
            }
        }.asLiveData()

    override fun getFavoriteFood(): LiveData<List<Food>> {
        return Transformations.map(localDataSource.getFavoriteFood()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteFood(food: Food, state: Boolean) {
        val foodEntity = DataMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { localDataSource.setFavoriteFood(foodEntity, state) }
    }
}