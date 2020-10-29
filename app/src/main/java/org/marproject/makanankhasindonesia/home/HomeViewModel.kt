package org.marproject.makanankhasindonesia.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class HomeViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val food = foodUseCase.getAllFood().asLiveData()
}