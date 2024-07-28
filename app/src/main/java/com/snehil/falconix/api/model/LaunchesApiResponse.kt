package com.snehil.falconix.api.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launches")
data class LaunchData (
  @PrimaryKey
  @SerializedName("_id") var id                                         : String                = "",
  @SerializedName("mission_name"            ) var missionName           : String?               = null,
  @SerializedName("launch_year"             ) var launchYear            : String?               = null,
  @SerializedName("launch_date_unix"        ) var launchDateUnix        : Int?                  = null,
  @SerializedName("launch_date_utc"         ) var launchDateUtc         : String?               = null,
  @SerializedName("launch_date_local"       ) var launchDateLocal       : String?               = null,
  @Ignore @SerializedName("rocket"                  ) var rocket        : Rocket                = Rocket(),
  @Embedded @SerializedName("launch_site"             ) var launchSite            : LaunchSite?           = LaunchSite(),
  @Embedded @SerializedName("links"                   ) var links: Links?                = Links(),
  var pageNo : Int = 0
)

data class LaunchWithRocket(
    @Embedded
    val launchData: LaunchData,

    @Relation(
        entity = Rocket::class,
        parentColumn = "id",
        entityColumn = "rocketId",
        associateBy = Junction(LaunchRocketEntity::class)
    )
    val rockets: List<RocketWithPayload>
)