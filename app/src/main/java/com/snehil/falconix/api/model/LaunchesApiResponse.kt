package com.snehil.falconix.api.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class LaunchesApiResponse(
  val launches: List<LaunchData>
)

@Entity(tableName = "launches")
data class LaunchData (
  @PrimaryKey
  @SerializedName("_id") var id: String,
  @SerializedName("mission_name"            ) var missionName           : String?               = null,
  @SerializedName("launch_year"             ) var launchYear            : String?               = null,
  @SerializedName("launch_date_unix"        ) var launchDateUnix        : Int?                  = null,
  @SerializedName("launch_date_utc"         ) var launchDateUtc         : String?               = null,
  @SerializedName("launch_date_local"       ) var launchDateLocal       : String?               = null,
  @Embedded @SerializedName("rocket"                  ) var rocket      : Rocket?               = Rocket(),
  @SerializedName("launch_site"             ) var launchSite            : LaunchSite?           = LaunchSite(),
  @SerializedName("links"                   ) var links                 : Links?                = Links(),
)