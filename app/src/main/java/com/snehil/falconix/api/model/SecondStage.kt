package com.snehil.falconix.api.model

import androidx.room.Relation
import com.google.gson.annotations.SerializedName


data class SecondStage (

  @Relation(
    parentColumn = "rocket_id",
    entityColumn = "rocket_id"
  )
  @SerializedName("payloads" ) var payloads : ArrayList<Payloads> = arrayListOf()

)