package org.marproject.makanankhasindonesia.core.data.source.local.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods")
    fun getAllFood(): Flowable<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE isFavorite = 1")
    fun getFavoriteFood(): Flowable<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: List<FoodEntity>): Completable

    @Update
    fun updateFavoriteFood(food: FoodEntity)
}