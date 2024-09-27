package com.snehil.ricky.ui.characters.list

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.snehil.ricky.R
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.ui.components.CharacterCard
import com.snehil.ricky.ui.components.StateIndicator
import com.snehil.ricky.ui.theme.LocalSpacings


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.EpisodeList(
    navController: NavController,
    state: LazyListState,
    query: MutableState<String>,
    list: LazyPagingItems<CharacterEntity>,
    animatedContentScope: AnimatedContentScope
) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            EmbeddedSearchBar(onQueryChange = {
                query.value = it
            })
            InternalCharacterList(
                list, state, navController, animatedContentScope
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EmbeddedSearchBar(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onSearch: ((String) -> Unit) = {},
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    BackHandler(searchQuery.isNotBlank()) {
        searchQuery = ""
        onQueryChange("")
    }
    val activeChanged: (Boolean) -> Unit = { _ ->
        searchQuery = ""
        onQueryChange("")
    }
    val colors1 = SearchBarDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    )
    val padding = LocalSpacings.current.sm
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    onQueryChange(query)
                },
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = activeChanged,
                placeholder = { Text("Search by name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        val keyboardController = LocalSoftwareKeyboardController.current
                        Icon(imageVector = Icons.Rounded.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable {
                                searchQuery = ""
                                onQueryChange("")
                                keyboardController?.hide()
                            })
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                colors = colors1.inputFieldColors,
            )
        },
        expanded = false,
        onExpandedChange = activeChanged,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier.padding(
            start = padding, end = padding, bottom = padding
        ).fillMaxWidth(),
        colors = colors1,
        tonalElevation = 0.dp,
        content = {},
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.InternalCharacterList(
    list: LazyPagingItems<CharacterEntity>,
    listState: LazyListState,
    navController: NavController,
    animatedContentScope: AnimatedContentScope
) {
    val viewModel = hiltViewModel<CharactersListViewModel>()

    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StateIndicator(list.loadState.append, list)
        }
        item {
            StateIndicator(list.loadState.refresh, list)
        }
        if (list.itemCount == 0 && list.loadState.isIdle) {
            item {
                Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                    Text(style = MaterialTheme.typography.titleLarge, text = stringResource(R.string.no_results_found))
                }
            }
        }
        items(list.itemCount, key = {
            list[it]?.characterId ?: it
        }) {
            val item = list[it]
            val id = item?.characterId

            @Composable
            fun card() {
                val episodeId = remember {
                    item?.firstEpisodeUrl?.substringAfterLast("/")?.toIntOrNull()
                }
                val episodeNameEntity by viewModel.getEpisodeName(episodeId)
                    .collectAsStateWithLifecycle(null)
                CharacterCard(animatedContentScope, navController, item, {
                    viewModel.loadEpisodeName(id, episodeId)
                }, episodeNameEntity?.name) {
                    viewModel.toggleFavourite(id)
                }
            }
            if (it < list.itemCount - 1) {
                Column {
                    card()
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = LocalSpacings.current.sm),
                        color = Color(0xCAC4C4C4),
                        thickness = 1.dp
                    )
                }
            } else {
                card()
            }
        }
        item {
            StateIndicator(list.loadState.append, list)
        }
    }
}
