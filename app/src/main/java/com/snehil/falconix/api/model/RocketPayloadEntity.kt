package com.snehil.falconix.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["payloadId","rocketId"],
    foreignKeys = [
        ForeignKey(
            entity =  Rocket::class,
            parentColumns = ["rocketId"],
            childColumns = ["rocketId"]
        ), ForeignKey(
            entity = Payload::class,
            parentColumns = ["payloadId"],
            childColumns = ["payloadId"]
        )
    ]
)
data class RocketPayloadEntity(
    @ColumnInfo(index = true)
    var rocketId    : String,
    var payloadId   : String,
)