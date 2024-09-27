package com.snehil.ricky.ui.characters.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.snehil.ricky.R
import com.snehil.ricky.Routes
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.ui.components.FavouriteButton
import com.snehil.ricky.ui.components.ShimmerImage
import com.snehil.ricky.ui.theme.LocalSpacings

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.Details(
    navController: NavHostController,
    arg: Routes.DETAILS,
    animatedContentScope: AnimatedContentScope
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val character by viewModel.getCharacter(arg.id).collectAsStateWithLifecycle(null)
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = character?.name ?: "")
            }, navigationIcon = {
                Icon(rememberVectorPainter(Icons.AutoMirrored.Rounded.ArrowBack),
                    "Back",
                    modifier = Modifier.padding(
                        LocalSpacings.current.sm
                    ).clickable {
                        navController.popBackStack()
                    })
            }, actions = {
                FavouriteButton(
                    modifier = Modifier.padding(
                        LocalSpacings.current.sm
                    ), isFavourite = character?.isFavourite ?: false
                ) {
                    viewModel.toggleFavourite(character?.characterId ?: -1)
                }
            })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.padding(horizontal = LocalSpacings.current.sm),
                verticalArrangement = Arrangement.spacedBy(LocalSpacings.current.sm)
            ) {
                character?.let { CharacterDetails(it, arg, animatedContentScope) }
                if (character == null) {
                    ShowEpisodeNotFoundAlert()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterDetails(
    characterEntity: CharacterEntity,
    arg: Routes.DETAILS,
    animatedContentScope: AnimatedContentScope
) {
    Row(horizontalArrangement = Arrangement.spacedBy(LocalSpacings.current.sm)) {
        ShimmerImage(
            modifier = Modifier.size(136.dp),
            animatedContentScope,
            arg.coverUrl ?: "",
            arg.id
        )
        Text(
            text = characterEntity.name ?: "", style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.sharedElement(
                rememberSharedContentState(key = -arg.id),
                animatedVisibilityScope = animatedContentScope
            )
        )
    }
    Text(
        text = characterEntity.created ?: "",
        style = MaterialTheme.typography.labelSmall
    )
    Text(
        text = stringResource(R.string.id, characterEntity.characterId),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(R.string.status, characterEntity.status ?: ""),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(R.string.species, characterEntity.species ?: ""),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(R.string.gender, characterEntity.gender ?: ""),
        style = MaterialTheme.typography.bodyMedium
    )
}


@Composable
fun ShowEpisodeNotFoundAlert() {


}
