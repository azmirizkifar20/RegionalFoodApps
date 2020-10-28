package org.marproject.makanankhasindonesia.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.di.Injection
import org.marproject.makanankhasindonesia.detail.DetailFoodViewModel
import org.marproject.makanankhasindonesia.favorite.FavoriteViewModel
import org.marproject.makanankhasindonesia.home.HomeViewModel

class ViewModelFactory private constructor(private val foodRepository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideRepository(
                                context
                            )
                        )
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(DetailFoodViewModel::class.java) -> {
                DetailFoodViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(foodRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}