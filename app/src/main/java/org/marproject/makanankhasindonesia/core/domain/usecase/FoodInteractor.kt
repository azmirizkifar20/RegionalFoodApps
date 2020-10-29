package org.marproject.makanankhasindonesia.core.domain.usecase

import io.reactivex.Flowable
import org.marproject.makanankhasindonesia.core.data.FoodRepository
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

class FoodInteractor(
    private val foodRepository: FoodRepository
) : FoodUseCase {
    override fun getAllFood(): Flowable<Resource<List<Food>>> = foodRepository.getAllFood()

    override fun getFavoriteFood(): Flowable<List<Food>> = foodRepository.getFavoriteFood()

    override fun setFavoriteFood(food: Food, state: Boolean) = foodRepository.setFavoriteFood(food, state)
}