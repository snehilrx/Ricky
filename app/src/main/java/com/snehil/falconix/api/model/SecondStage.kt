package com.snehil.falconix.api.model

import com.google.gson.annotations.SerializedName


data class SecondStage(

    @SerializedName("payloads") var payloads: List<Payload>? = arrayListOf()

)