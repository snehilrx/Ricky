package com.snehil.falconix.api.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Payload(
    @PrimaryKey
    @SerializedName("payload_id") var payloadId: String,
    @SerializedName("norad_id") var noradId: List<String> = arrayListOf(),
    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("customers") var customers: List<String> = arrayListOf(),
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("manufacturer") var manufacturer: String? = null,
    @SerializedName("payload_type") var payloadType: String? = null,
    @SerializedName("payload_mass_kg") var payloadMassKg: Double? = null,
    @SerializedName("payload_mass_lbs") var payloadMassLbs: Double? = null,
    @SerializedName("orbit") var orbit: String? = null,
    @Embedded
    @SerializedName("orbit_params") var orbitParams: OrbitParams? = OrbitParams()

)