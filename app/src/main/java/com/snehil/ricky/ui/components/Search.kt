package com.snehil.ricky.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.snehil.ricky.ui.theme.LocalSpacings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavHostController) {
    // search bar
    Column {
        var query by remember { mutableStateOf("") }

        val homeViewModel = hiltViewModel<HomeViewModel>()
        val items = homeViewModel.getPager(query).collectAsLazyPagingItems()
        LaunchesList(items, navController) {
            SearchBar(
                modifier = Modifier.fillMaxWidth().padding(horizontal = LocalSpacings.current.xs),
                query = query,
                onQueryChange = { query = it },
                onSearch = { query = it },
                active = false,
                onActiveChange = {},
                placeholder = { Text("Search") },
                trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") }
            ) {
            }
        }
    }

}