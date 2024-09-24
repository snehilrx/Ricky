package com.snehil.ricky.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (

    @SerializedName("id"        ) var id        : Int?              = null,
    @SerializedName("name"      ) var name      : String?           = null,
    @SerializedName("type"      ) var type      : String?           = null,
    @SerializedName("dimension" ) var dimension : String?           = null,
    @SerializedName("residents" ) private var residents : ArrayList<String> = arrayListOf(),
    @SerializedName("url"       ) var url       : String?           = null,
    @SerializedName("created"   ) var created   : String?           = null

) : Parcelable