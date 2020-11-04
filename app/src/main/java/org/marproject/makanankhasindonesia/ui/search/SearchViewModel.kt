package org.marproject.makanankhasindonesia.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class SearchViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val food = foodUseCase.getAllFood().asLiveData()
}