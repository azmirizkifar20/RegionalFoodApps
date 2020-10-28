package org.marproject.makanankhasindonesia.core.utils

import org.marproject.makanankhasindonesia.core.data.source.local.entity.FoodEntity
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse

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
}