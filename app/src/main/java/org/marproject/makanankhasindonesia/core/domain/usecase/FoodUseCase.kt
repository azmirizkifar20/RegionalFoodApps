package org.marproject.makanankhasindonesia.core.domain.usecase

import androidx.lifecycle.LiveData
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

interface FoodUseCase {
    fun getAllFood(): LiveData<Resource<List<Food>>>
    fun getFavoriteFood(): LiveData<List<Food>>
    fun setFavoriteFood(food: Food, state: Boolean)
}