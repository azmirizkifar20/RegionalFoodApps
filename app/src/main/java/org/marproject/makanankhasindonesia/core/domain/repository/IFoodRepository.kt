package org.marproject.makanankhasindonesia.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food

interface IFoodRepository {

    fun getAllFood(): Flow<Resource<List<Food>>>

    fun getFavoriteFood(): Flow<List<Food>>

    fun setFavoriteFood(food: Food, state: Boolean)
}