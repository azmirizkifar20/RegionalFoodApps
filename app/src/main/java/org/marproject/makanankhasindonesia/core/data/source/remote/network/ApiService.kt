package org.marproject.makanankhasindonesia.core.data.source.remote.network

import org.marproject.makanankhasindonesia.core.data.source.remote.response.ListFoodResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("food.json?alt=media&token=986aa1f8-994f-4df1-8d46-6fd5d2c014aa")
    fun getList(): Call<ListFoodResponse>
}