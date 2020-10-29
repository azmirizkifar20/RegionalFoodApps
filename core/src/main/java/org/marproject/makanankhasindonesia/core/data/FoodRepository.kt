package org.marproject.makanankhasindonesia.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
import org.marproject.makanankhasindonesia.core.data.source.remote.RemoteDataSource
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.domain.repository.IFoodRepository
import org.marproject.makanankhasindonesia.core.utils.AppExecutors
import org.marproject.makanankhasindonesia.core.utils.DataMapper

class FoodRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    override fun getAllFood(): Flow<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, List<FoodResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Food>> {
                return localDataSource.getAllFood().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Food>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<FoodResponse>>> =
                remoteDataSource.getAllFood()

            override suspend fun saveCallResult(data: List<FoodResponse>) {
                val foodList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertFood(foodList)
            }
        }.asFlow()

    override fun getFavoriteFood(): Flow<List<Food>> {
        return localDataSource.getFavoriteFood().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteFood(food: Food, state: Boolean) {
        val foodEntity = DataMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { localDataSource.setFavoriteFood(foodEntity, state) }
    }
}