package org.marproject.makanankhasindonesia.core.data.source.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods")
    fun getAllFood(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE isFavorite = 1")
    fun getFavoriteFood(): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: List<FoodEntity>)

    @Update
    fun updateFavoriteFood(food: FoodEntity)
}