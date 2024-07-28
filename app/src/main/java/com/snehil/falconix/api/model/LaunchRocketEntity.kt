package com.snehil.falconix.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["id", "rocketId"],
    foreignKeys = [
        ForeignKey(
            entity = Rocket::class,
            parentColumns = ["rocketId"],
            childColumns = ["rocketId"]
        ), ForeignKey(
            entity = LaunchData::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
    ]
)
data class LaunchRocketEntity(
    @ColumnInfo(index = true)
    var rocketId: String,
    var id: String,
)