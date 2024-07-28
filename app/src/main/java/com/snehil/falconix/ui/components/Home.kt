package com.snehil.falconix.ui.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun Home() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val items = homeViewModel.getPager().collectAsLazyPagingItems()
    LaunchesList(items)

}

