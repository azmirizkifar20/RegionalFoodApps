package org.marproject.makanankhasindonesia.core.di

import android.content.Context
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
import org.marproject.makanankhasindonesia.core.data.source.local.room.FoodDatabase
import org.marproject.makanankhasindonesia.core.data.source.remote.RemoteDataSource
import org.marproject.makanankhasindonesia.core.utils.AppExecutors
import org.marproject.makanankhasindonesia.core.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context): FoodRepository {
        val database = FoodDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.foodDao())
        val appExecutors = AppExecutors()

        return FoodRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}