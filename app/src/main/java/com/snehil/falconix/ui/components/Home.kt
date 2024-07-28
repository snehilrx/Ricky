package com.snehil.falconix.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.snehil.falconix.ui.theme.LocalCellSizes
import com.snehil.falconix.ui.theme.LocalSpacings
import com.snehil.falconix.ui.theme.LocalWindowSize
import com.snehil.falconix.ui.theme.WindowSize

@Composable
fun Home() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val items = homeViewModel.getPager().collectAsLazyPagingItems()
    LaunchesList(items)

}

