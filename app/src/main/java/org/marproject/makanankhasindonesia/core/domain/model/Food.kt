package org.marproject.makanankhasindonesia.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val foodId: Int,
    var place: String,
    var name: String,
    var image: String,
    var description: String,
    var isFavorite: Boolean
) : Parcelable