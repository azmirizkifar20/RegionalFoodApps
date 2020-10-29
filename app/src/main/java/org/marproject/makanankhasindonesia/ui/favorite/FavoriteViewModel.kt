package org.marproject.makanankhasindonesia.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class FavoriteViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val favoriteFood = foodUseCase.getFavoriteFood().asLiveData()
}