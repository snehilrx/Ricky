package com.snehil.ricky

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data class DETAILS(val id: Int, val coverUrl: String?) : Routes()

    @Serializable
    data object HOME : Routes()
}