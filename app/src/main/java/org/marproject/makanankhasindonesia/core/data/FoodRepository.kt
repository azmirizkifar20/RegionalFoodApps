package org.marproject.makanankhasindonesia.core.data

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
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

    override fun getAllFood(): Flowable<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, List<FoodResponse>>() {
            override fun loadFromDB(): Flowable<List<Food>> {
                return localDataSource.getAllFood().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Food>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): Flowable<ApiResponse<List<FoodResponse>>> =
                remoteDataSource.getAllFood()

            override fun saveCallResult(data: List<FoodResponse>) {
                val foodList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertFood(foodList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }.asFlowable()

    override fun getFavoriteFood(): Flowable<List<Food>> {
        return localDataSource.getFavoriteFood().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteFood(food: Food, state: Boolean) {
        val foodEntity = DataMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { localDataSource.setFavoriteFood(foodEntity, state) }
    }
}