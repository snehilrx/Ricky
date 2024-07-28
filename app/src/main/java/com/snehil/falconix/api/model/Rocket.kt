package com.snehil.falconix.api.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName


data class Rocket (

    @SerializedName("rocket_id"    ) var rocketId    : String?      = null,
    @SerializedName("rocket_name"  ) var rocketName  : String?      = null,
    @Embedded
    @SerializedName("second_stage" ) var secondStage : SecondStage? = SecondStage(),

)