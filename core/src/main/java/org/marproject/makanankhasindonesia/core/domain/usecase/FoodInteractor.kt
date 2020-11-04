package org.marproject.makanankhasindonesia.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

class FoodInteractor(
    private val foodRepository: FoodRepository
) : FoodUseCase {
    override fun getAllFood(): Flow<Resource<List<Food>>> = foodRepository.getAllFood()

    override fun getFavoriteFood(): Flow<List<Food>> = foodRepository.getFavoriteFood()

    override fun setFavoriteFood(food: Food, state: Boolean) = foodRepository.setFavoriteFood(food, state)
}