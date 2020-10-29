package org.marproject.makanankhasindonesia.core.data.source.local

import kotlinx.coroutines.flow.Flow
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.local.room.FoodDao

class LocalDataSource(private val foodDao: FoodDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(foodDao: FoodDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(foodDao)
            }
    }

    fun getAllFood(): Flow<List<FoodEntity>> = foodDao.getAllFood()

    fun getFavoriteFood(): Flow<List<FoodEntity>> = foodDao.getFavoriteFood()

    suspend fun insertFood(food: List<FoodEntity>) = foodDao.insertFood(food)

    fun setFavoriteFood(food: FoodEntity, newState: Boolean) {
        food.isFavorite = newState
        foodDao.updateFavoriteFood(food)
    }
}