package org.marproject.makanankhasindonesia.core.domain.usecase

import io.reactivex.Flowable
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

interface FoodUseCase {
    fun getAllFood(): Flowable<Resource<List<Food>>>
    fun getFavoriteFood(): Flowable<List<Food>>
    fun setFavoriteFood(food: Food, state: Boolean)
}