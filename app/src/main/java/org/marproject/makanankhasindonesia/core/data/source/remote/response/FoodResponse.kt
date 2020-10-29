package org.marproject.makanankhasindonesia.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("place")
    val place: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("description")
    val description: String
)