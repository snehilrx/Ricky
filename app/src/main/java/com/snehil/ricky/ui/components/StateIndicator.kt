package com.snehil.ricky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.snehil.ricky.R

@Composable
fun StateIndicator(state: LoadState, items: LazyPagingItems<*>) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is LoadState.Error -> {
                ErrorState(state, items)
            }

            is LoadState.Loading -> CircularProgressIndicator(
                modifier = Modifier.width(20.dp).height(20.dp)
            )

            is LoadState.NotLoading -> {}
        }
    }
}

@Composable
fun ErrorState(state: LoadState.Error, items: LazyPagingItems<*>) {
    Text(state.error.message ?: stringResource(R.string.something_went_wrong))
    Button(onClick = {
        items.retry()
    }) {
        Text(stringResource(R.string.retry))
    }
}