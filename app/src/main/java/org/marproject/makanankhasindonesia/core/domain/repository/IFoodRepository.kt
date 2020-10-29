package org.marproject.makanankhasindonesia.core.domain.repository

import io.reactivex.Flowable
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

interface IFoodRepository {

    fun getAllFood(): Flowable<Resource<List<Food>>>

    fun getFavoriteFood(): Flowable<List<Food>>

    fun setFavoriteFood(food: Food, state: Boolean)
}