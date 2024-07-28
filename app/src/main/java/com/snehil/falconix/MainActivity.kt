package com.snehil.falconix

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.snehil.falconix.ui.components.Details
import com.snehil.falconix.ui.components.Home
import com.snehil.falconix.ui.components.Search
import com.snehil.falconix.ui.components.Store
import com.snehil.falconix.ui.components.Tabs
import com.snehil.falconix.ui.theme.FalconIXTheme
import com.snehil.falconix.ui.theme.LocalWindowSize
import com.snehil.falconix.ui.theme.WindowSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FalconIXTheme(rememberWindowSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME.route
                ) {
                    composable(Routes.HOME.route) {
                        MainScreen(navController)
                    }
                    composable(Routes.DETAILS.route) {
                        Details()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScreen(navController: NavHostController) {
        val tabsNavController = rememberNavController()
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("FALCONE IX")
                    }
                )
            },
            bottomBar = {
                if (LocalWindowSize.current <= WindowSize.COMPACT) {
                    val scrollState = rememberScrollState()
                    BottomAppBar(
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.horizontalScroll(scrollState)
                            ) {
                                Tabs(tabsNavController)
                            }
                        }
                    }
                }
            }
        ) {
            Box(Modifier.padding(it), contentAlignment = Alignment.Center) {
                Row {
                    if (LocalWindowSize.current > WindowSize.COMPACT) {
                        val scrollState = rememberScrollState()
                        NavigationRail(
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Column(
                                modifier = Modifier.verticalScroll(scrollState)
                            ) {
                                Tabs(tabsNavController)
                            }
                        }
                    }

                    NavHost(
                        navController = tabsNavController,
                        startDestination = Routes.BottomNavRoutes.HOME.route
                    ) {
                        composable(Routes.BottomNavRoutes.SEARCH.route) {
                            Search()
                        }
                        composable(Routes.BottomNavRoutes.HOME.route) {
                            Home()
                        }
                        composable(Routes.BottomNavRoutes.STORE.route) {
                            Store()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Activity.rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val windowMetrics = remember(configuration) {
        WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)
    }
    val windowDpSize = with(LocalDensity.current) {
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }
    return WindowSize.basedOnWidth(windowDpSize.width)
}