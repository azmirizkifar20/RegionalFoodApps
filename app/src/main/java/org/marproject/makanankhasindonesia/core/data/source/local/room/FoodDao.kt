package org.marproject.makanankhasindonesia.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods")
    fun getAllFood(): LiveData<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE isFavorite = 1")
    fun getFavoriteFood(): LiveData<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: List<FoodEntity>)

    @Update
    fun updateFavoriteFood(food: FoodEntity)
}