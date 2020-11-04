package org.marproject.makanankhasindonesia.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity

@Database(entities = [FoodEntity::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

}