package org.marproject.makanankhasindonesia.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "foodId")
    var foodId: Int,

    @ColumnInfo(name = "place")
    var place: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable