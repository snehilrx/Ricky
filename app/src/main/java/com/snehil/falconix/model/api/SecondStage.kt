package com.snehil.falconix.model.api

import com.google.gson.annotations.SerializedName
import com.snehil.falconix.model.api.Payloads


data class SecondStage (

  @SerializedName("block"    ) var block    : Int?                = null,
  @SerializedName("payloads" ) var payloads : ArrayList<Payloads> = arrayListOf()

)