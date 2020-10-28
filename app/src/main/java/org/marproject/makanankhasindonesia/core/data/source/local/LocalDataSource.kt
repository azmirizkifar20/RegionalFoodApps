package org.marproject.makanankhasindonesia.core.data.source.local

import androidx.lifecycle.LiveData
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.local.room.FoodDao

class LocalDataSource private constructor(private val foodDao: FoodDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(foodDao: FoodDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(foodDao)
            }
    }

    fun getAllFood(): LiveData<List<FoodEntity>> = foodDao.getAllFood()

    fun getFavoriteFood(): LiveData<List<FoodEntity>> = foodDao.getFavoriteFood()

    fun insertFood(food: List<FoodEntity>) = foodDao.insertFood(food)

    fun setFavoriteFood(food: FoodEntity, newState: Boolean) {
        food.isFavorite = newState
        foodDao.updateFavoriteFood(food)
    }
}