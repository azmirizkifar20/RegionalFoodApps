package org.marproject.makanankhasindonesia.core.data

import androidx.lifecycle.LiveData
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.remote.RemoteDataSource
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.utils.AppExecutors
import org.marproject.makanankhasindonesia.core.utils.DataMapper

class FoodRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) {

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

    fun getAllFood(): LiveData<Resource<List<FoodEntity>>> =
        object : NetworkBoundResource<List<FoodEntity>, List<FoodResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<FoodEntity>> {
                return localDataSource.getAllFood()
            }

            override fun shouldFetch(data: List<FoodEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<FoodResponse>>> =
                remoteDataSource.getAllFood()

            override fun saveCallResult(data: List<FoodResponse>) {
                val foodList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertFood(foodList)
            }

        }.asLiveData()

    fun getFavoriteFood(): LiveData<List<FoodEntity>> {
        return localDataSource.getFavoriteFood()
    }

    fun setFavoriteFood(food: FoodEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteFood(food, state) }
    }
}