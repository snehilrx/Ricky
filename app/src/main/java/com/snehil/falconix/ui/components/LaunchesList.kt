package com.snehil.falconix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.snehil.falconix.DETAILS_RAW_ROUTE
import com.snehil.falconix.R
import com.snehil.falconix.api.model.LaunchWithRocket
import com.snehil.falconix.ui.theme.LocalCellSizes
import com.snehil.falconix.ui.theme.LocalSpacings
import com.snehil.falconix.ui.theme.LocalWindowSize
import com.snehil.falconix.ui.theme.WindowSize

@Composable
fun LaunchesList(items: LazyPagingItems<LaunchWithRocket>, navController: NavHostController) {
    val columns = GridCells.Adaptive(
        if (LocalWindowSize.current < WindowSize.MEDIUM) {
            LocalCellSizes.current.xxl
        } else {
            LocalCellSizes.current.xxxl
        }
    )
    LazyVerticalGrid(
        columns = columns,
        contentPadding = PaddingValues(LocalSpacings.current.xs),
    ) {
        item(span = {
            GridItemSpan(maxCurrentLineSpan)
        }) {
            StateIndicator(items.loadState.refresh, items)
        }
        item(span = {
            GridItemSpan(maxCurrentLineSpan)
        }) {
            StateIndicator(items.loadState.prepend, items)
        }
        items(items.itemCount) { index ->
            items[index]?.let {
                LaunchCard(it) {
                    navController.navigate("${DETAILS_RAW_ROUTE}${it.launchData.id}")
                }
            }
        }
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            StateIndicator(items.loadState.append, items)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LaunchCard(data: LaunchWithRocket, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(LocalSpacings.current.xs).height(LocalCellSizes.current.xxxl)
    ) {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = 200f,
            endY = 800f
        )
        Box(contentAlignment = Alignment.BottomStart) {
            GlideImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                model = data.launchData.links?.flickrImages?.firstOrNull() ?: "",
                contentDescription = data.launchData.missionName
            )
            Box(modifier = Modifier.matchParentSize().background(gradient))
            Column(
                modifier = Modifier.padding(LocalSpacings.current.xs),
                verticalArrangement = Arrangement.spacedBy(
                    LocalSpacings.current.xs
                )
            ) {
                Text(data.launchData.missionName ?: "", maxLines = 1, color = Color.White)
                Text(data.launchData.launchYear ?: "", maxLines = 1, color = Color.White)
                Text(data.rockets.firstOrNull()?.rocket?.rocketName ?: "", maxLines = 1, color = Color.White)
            }
        }
    }
}

@Composable
fun StateIndicator(state: LoadState, items: LazyPagingItems<LaunchWithRocket>) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is LoadState.Error -> {
                Text(state.error.message ?: stringResource(R.string.something_went_wrong))
                Button(onClick = {
                    items.retry()
                }) {
                    Text(stringResource(R.string.retry))
                }
            }

            is LoadState.Loading ->
                CircularProgressIndicator(
                    modifier = Modifier.width(20.dp).height(20.dp)
                )

            is LoadState.NotLoading -> {}
        }
    }
}