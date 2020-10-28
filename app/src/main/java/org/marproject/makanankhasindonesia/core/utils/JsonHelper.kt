package org.marproject.makanankhasindonesia.core.utils

import android.content.Context
import org.json.JSONObject
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.core.data.source.remote.response.FoodResponse
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.food).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData(): List<FoodResponse> {
        val list = ArrayList<FoodResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("data")

        for (i in 0 until listArray.length()) {
            val data = listArray.getJSONObject(i)

            val id = data.getInt("id")
            val place = data.getString("place")
            val name = data.getString("name")
            val image = data.getString("image")
            val description = data.getString("description")

            val foodData = FoodResponse(
                id = id,
                place = place,
                name = name,
                image = image,
                description = description
            )

            list.add(foodData)
        }
        return list
    }
}