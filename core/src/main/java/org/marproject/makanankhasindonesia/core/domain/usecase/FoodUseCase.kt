package org.marproject.makanankhasindonesia.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

interface FoodUseCase {
    fun getAllFood(): Flow<Resource<List<Food>>>
    fun getFavoriteFood(): Flow<List<Food>>
    fun setFavoriteFood(food: Food, state: Boolean)
}