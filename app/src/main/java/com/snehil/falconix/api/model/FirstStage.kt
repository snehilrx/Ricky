package com.snehil.falconix.api.model

import com.google.gson.annotations.SerializedName


data class FirstStage (

  @SerializedName("cores" ) var cores : ArrayList<Cores> = arrayListOf()

)