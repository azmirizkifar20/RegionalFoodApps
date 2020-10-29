package org.marproject.makanankhasindonesia.detail

import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class DetailFoodViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    fun setFavoriteFood(food: Food, status: Boolean) = foodUseCase.setFavoriteFood(food, status)
}