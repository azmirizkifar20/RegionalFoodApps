package org.marproject.makanankhasindonesia.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.marproject.makanankhasindonesia.core.di.Injection
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase
import org.marproject.makanankhasindonesia.detail.DetailFoodViewModel
import org.marproject.makanankhasindonesia.favorite.FavoriteViewModel
import org.marproject.makanankhasindonesia.home.HomeViewModel

class ViewModelFactory private constructor(private val foodUseCase: FoodUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideFoodUseCase(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(DetailFoodViewModel::class.java) -> {
                DetailFoodViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(foodUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}