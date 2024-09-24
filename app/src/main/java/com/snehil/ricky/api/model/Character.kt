package com.snehil.ricky.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character (

    @SerializedName("id"       ) var id       : Int?              = null,
    @SerializedName("name"     ) var name     : String?           = null,
    @SerializedName("status"   ) var status   : String?           = null,
    @SerializedName("species"  ) var species  : String?           = null,
    @SerializedName("type"     ) var type     : String?           = null,
    @SerializedName("gender"   ) var gender   : String?           = null,
    @SerializedName("origin"   ) var origin   : Origin?           = Origin(),
    @SerializedName("location" ) var location : Location?         = Location(),
    @SerializedName("image"    ) var image    : String?           = null,
    @SerializedName("episode"  ) var episode  : ArrayList<String> = arrayListOf(),
    @SerializedName("url"      ) var url      : String?           = null,
    @SerializedName("created"  ) var created  : String?           = null

) : Parcelable {

    @Parcelize
    data class Location (

        @SerializedName("name" ) var name : String? = null,
        @SerializedName("url"  ) var url  : String? = null

    ) : Parcelable

    @Parcelize
    data class Origin (

        @SerializedName("name" ) var name : String? = null,
        @SerializedName("url"  ) var url  : String? = null

    ) : Parcelable
}