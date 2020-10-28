package org.marproject.makanankhasindonesia.core.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONException
import org.marproject.makanankhasindonesia.core.data.source.remote.network.ApiResponse
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import org.marproject.makanankhasindonesia.core.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllFood(): LiveData<ApiResponse<List<FoodResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<FoodResponse>>>()

        // get data from local to json
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            try {
                val dataArray = jsonHelper.loadData()
                if (dataArray.isNotEmpty()) {
                    resultData.value = ApiResponse.Success(dataArray)
                } else {
                    resultData.value = ApiResponse.Empty
                }

            } catch (e: JSONException) {
                resultData.value = ApiResponse.Error(e.toString())
                Log.e("RemoteDataSource", e.toString())
            }
        }, 2000)

        return resultData
    }
}