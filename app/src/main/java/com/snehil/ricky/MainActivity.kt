package com.snehil.ricky

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.layout.WindowMetricsCalculator
import com.snehil.ricky.ui.characters.details.Details
import com.snehil.ricky.ui.characters.list.CharactersListViewModel
import com.snehil.ricky.ui.characters.list.EpisodeList
import com.snehil.ricky.ui.theme.RickyTheme
import com.snehil.ricky.ui.theme.WindowSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickyTheme(rememberWindowSize()) {
                val navController = rememberNavController()
                SharedTransitionLayout {

                    val viewModel = hiltViewModel<CharactersListViewModel>()
                    val listState = rememberLazyListState()
                    val query = remember { mutableStateOf("") }

                    val list by remember(query) {
                        derivedStateOf {
                            viewModel.pager(query.value)
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME
                    ) {
                        composable<Routes.HOME> {
                            EpisodeList(
                                navController,
                                listState,
                                query,
                                list.collectAsLazyPagingItems(),
                                this@composable
                            )
                        }
                        composable<Routes.DETAILS> { backStackEntry ->
                            val arg: Routes.DETAILS = backStackEntry.toRoute()
                            Details(navController, arg, this@composable)
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