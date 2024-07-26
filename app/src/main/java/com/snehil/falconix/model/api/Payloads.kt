package com.snehil.falconix.model.api

import com.google.gson.annotations.SerializedName
import com.snehil.falconix.model.api.OrbitParams


data class Payloads (

  @SerializedName("payload_id"       ) var payloadId      : String?           = null,
  @SerializedName("norad_id"         ) var noradId        : ArrayList<String> = arrayListOf(),
  @SerializedName("reused"           ) var reused         : Boolean?          = null,
  @SerializedName("customers"        ) var customers      : ArrayList<String> = arrayListOf(),
  @SerializedName("nationality"      ) var nationality    : String?           = null,
  @SerializedName("manufacturer"     ) var manufacturer   : String?           = null,
  @SerializedName("payload_type"     ) var payloadType    : String?           = null,
  @SerializedName("payload_mass_kg"  ) var payloadMassKg  : Int?              = null,
  @SerializedName("payload_mass_lbs" ) var payloadMassLbs : Int?              = null,
  @SerializedName("orbit"            ) var orbit          : String?           = null,
  @SerializedName("orbit_params"     ) var orbitParams    : OrbitParams?      = OrbitParams()

)