package org.marproject.makanankhasindonesia.core.utils

import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.domain.model.Food

object DataMapper {

    fun mapResponseToEntities(input: List<FoodResponse>): List<FoodEntity> {
        val foodList = ArrayList<FoodEntity>()

        input.map {
            val food = FoodEntity(
                foodId = it.id,
                place = it.place,
                name = it.name,
                image = it.image,
                description = it.description,
                isFavorite = false
            )
            foodList.add(food)
        }

        return foodList
    }

    fun mapEntitiesToDomain(input: List<FoodEntity>): List<Food> =
        input.map {
            Food(
                foodId = it.foodId,
                place = it.place,
                name = it.name,
                image = it.image,
                description = it.description,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Food) = FoodEntity(
        foodId = input.foodId,
        place = input.place,
        name = input.name,
        image = input.image,
        description = input.description,
        isFavorite = input.isFavorite
    )
}