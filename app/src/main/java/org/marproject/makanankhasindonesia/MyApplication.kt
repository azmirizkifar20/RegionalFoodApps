package org.marproject.makanankhasindonesia

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.marproject.makanankhasindonesia.core.di.databaseModule
import org.marproject.makanankhasindonesia.core.di.networkModule
import org.marproject.makanankhasindonesia.core.di.repositoryModule
import org.marproject.makanankhasindonesia.di.useCaseModule
import org.marproject.makanankhasindonesia.di.viewModelModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}