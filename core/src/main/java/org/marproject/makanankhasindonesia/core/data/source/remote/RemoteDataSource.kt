package org.marproject.makanankhasindonesia.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiService
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllFood(): Flow<ApiResponse<List<FoodResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.data

                if (dataArray.isNotEmpty())
                    emit(ApiResponse.Success(response.data))
                 else
                    emit(ApiResponse.Empty)

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}