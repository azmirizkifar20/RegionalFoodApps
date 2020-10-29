package org.marproject.makanankhasindonesia.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase

class HomeViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val food = LiveDataReactiveStreams.fromPublisher(foodUseCase.getAllFood())
}