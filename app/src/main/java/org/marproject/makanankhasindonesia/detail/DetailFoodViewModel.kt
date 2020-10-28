package org.marproject.makanankhasindonesia.detail

import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity

class DetailFoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    fun setFavoriteFood(food: FoodEntity, status: Boolean) = foodRepository.setFavoriteFood(food, status)
}