package org.marproject.makanankhasindonesia.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiService
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.response.ListFoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllFood(): LiveData<ApiResponse<List<FoodResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<FoodResponse>>>()

        // get data from remote api
        val client = apiService.getList()

        client.enqueue(object : Callback<ListFoodResponse> {
            override fun onResponse(
                call: Call<ListFoodResponse>,
                response: Response<ListFoodResponse>
            ) {
                val dataArray = response.body()?.data
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }

        })

        return resultData
    }
}