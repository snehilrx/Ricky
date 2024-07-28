package com.snehil.falconix.ui.components

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snehil.falconix.HomeRoutes
import com.snehil.falconix.Routes

@Composable
fun Tabs(
    navController: NavHostController
) {
    HomeRoutes.forEach {
        TabNavigationItem(tab = it, navController)
    }
}

@Composable
private fun TabNavigationItem(tab: Routes.BottomNavRoutes, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.d("TabNavigationItem", "Current Route: $currentRoute, Tab Route: ${tab.route}")

    NavigationRailItem(
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.primary
        ),
        selected = currentRoute == tab.route,
        onClick = { navController.navigate(tab.route) },
        label = { Text(tab.label) },
        icon = { Icon(imageVector = tab.icon, contentDescription = tab.label) }
    )
}