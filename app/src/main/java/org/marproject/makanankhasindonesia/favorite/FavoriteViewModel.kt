package org.marproject.makanankhasindonesia.favorite

import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.data.FoodRepository

class FavoriteViewModel(foodRepository: FoodRepository) : ViewModel() {
    val favoriteFood = foodRepository.getFavoriteFood()
}