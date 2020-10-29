package org.marproject.makanankhasindonesia.favorite

import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class FavoriteViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val favoriteFood = foodUseCase.getFavoriteFood()
}