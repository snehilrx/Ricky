package com.snehil.falconix.ui.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun Home(navController: NavHostController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val items = homeViewModel.pager.collectAsLazyPagingItems()
    LaunchesList(items, navController)
}

