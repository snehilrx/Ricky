package com.snehil.ricky.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(

    @SerializedName("name") var name: String? = null,

    ) : Parcelable