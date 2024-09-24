package com.snehil.ricky

const val DETAILS_RAW_ROUTE = "/episode?id="

sealed class Routes(val route: String) {
    data object DETAILS : Routes("${DETAILS_RAW_ROUTE}{id}")
    data object HOME : Routes("/home")
}