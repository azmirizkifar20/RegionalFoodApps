package org.marproject.makanankhasindonesia.home

import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.data.FoodRepository

class HomeViewModel(foodRepository: FoodRepository) : ViewModel() {
    val food = foodRepository.getAllFood()
}