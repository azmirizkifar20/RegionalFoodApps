package org.marproject.makanankhasindonesia.core.di

import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.data.source.local.LocalDataSource
import org.marproject.makanankhasindonesia.core.data.source.local.room.FoodDatabase
import org.marproject.makanankhasindonesia.core.data.source.remote.RemoteDataSource
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiService
import org.marproject.makanankhasindonesia.core.utils.AppExecutors
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FoodDatabase>().foodDao() }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("marproject".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FoodDatabase::class.java,
            "Food.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "firebasestorage.googleapis.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/ikLJDXn+JjUmyrM8hrQniBSqiWCnkbY7xXANNf/BbAQ=")
            .add(hostname, "sha256/5j6bIxVylmLBIeyADL48ObCTTKoClAUnc1Tp5nRLsqw=")
            .add(hostname, "sha256/7rlXmYCbZOgh8zbA0JD9hsUELldvDrUyH5gHQiSzJsU=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/rizkifar-project.appspot.com/o/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single {
        FoodRepository(
            get(),
            get(),
            get()
        )
    }
}