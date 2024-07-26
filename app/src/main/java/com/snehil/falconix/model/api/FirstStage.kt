package com.snehil.falconix.model.api

import com.google.gson.annotations.SerializedName


data class FirstStage (

  @SerializedName("cores" ) var cores : ArrayList<Cores> = arrayListOf()

)