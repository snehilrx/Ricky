package com.snehil.falconix.api.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity
data class Rocket (
    @PrimaryKey
    @SerializedName("rocket_id"    ) var rocketId    : String = "",
    @SerializedName("rocket_name"  ) var rocketName  : String?      = null,
    @Ignore @SerializedName("second_stage" ) var secondStage : SecondStage? = SecondStage(),
)

data class RocketWithPayload(

    @Embedded
    val rocket: Rocket,

    @Relation(
        entity = Payload::class,
        parentColumn = "rocketId",
        entityColumn = "payloadId",
        associateBy = Junction(RocketPayloadEntity::class)
    )
    val payloads: List<Payload>
)
