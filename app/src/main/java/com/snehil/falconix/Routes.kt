package com.snehil.falconix

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.snehil.falconix.image.SpaceX

const val DETAILS_RAW_ROUTE = "/launchDetails?id="
sealed class  Routes(val route: String) {
    sealed class BottomNavRoutes(path: String, val label: String, val icon: ImageVector) : Routes(path) {
        data object HOME : BottomNavRoutes(path = "/home", label = "Home", icon = Icons.Filled.Home)
        data object SEARCH : BottomNavRoutes(path = "/search", label = "Search", icon = Icons.Filled.Search)
        data object STORE : BottomNavRoutes(path = "/store", label = "Store", icon = SpaceX)
    }
    data object DETAILS : Routes("${DETAILS_RAW_ROUTE}{id}")
    data object HOME : Routes("/home")
}

val HomeRoutes = arrayOf(Routes.BottomNavRoutes.HOME, Routes.BottomNavRoutes.SEARCH, Routes.BottomNavRoutes.STORE)