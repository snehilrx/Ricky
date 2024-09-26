package com.snehil.ricky.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.snehil.ricky.R
import com.snehil.ricky.Routes
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.images.NoImage
import com.snehil.ricky.ui.theme.LocalSpacings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterCard(
    animatedContentScope: AnimatedContentScope,
    navController: NavController,
    item: CharacterEntity?,
    loadEpisodeName: suspend () -> Unit,
    name: String?,
    onFavouriteClick: () -> Unit
) {
    var showAlertForNullId by remember { mutableStateOf(false) }
    val smallMargin = LocalSpacings.current.sm
    val xxs = LocalSpacings.current.xs
    val tags = remember {
        listOfNotNull(item?.gender, item?.species, item?.locationName)
    }
    LaunchedEffect(Unit) {
        loadEpisodeName()
    }

    ConstraintLayout(modifier = Modifier.clickable {
        if (item?.characterId != null) {
            navController.navigate(Routes.DETAILS(item.characterId, item.image))
        } else {
            showAlertForNullId = true
        }
    }.fillMaxWidth().padding(vertical = smallMargin)) {
        val (card, title, subtitle, episodeName, favouriteButton) = createRefs()


        ShimmerImage(
            Modifier.size(80.dp).constrainAs(card) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, smallMargin)
            }, animatedContentScope, item?.image ?: "", item?.characterId
        )

        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(card.end, smallMargin)
                end.linkTo(favouriteButton.start, smallMargin)
                width = fillToConstraints
            }.sharedElement(
                rememberSharedContentState(key = -(item?.characterId ?: 123819)),
                animatedVisibilityScope = animatedContentScope
            ),
            style = MaterialTheme.typography.titleLarge,
            text = item?.name ?: ""
        )
        Text(
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom, xxs)
                start.linkTo(card.end, smallMargin)
                end.linkTo(favouriteButton.start, smallMargin)
                width = fillToConstraints
            },
            style = MaterialTheme.typography.bodyMedium,
            text = tags.joinToString(separator = " • ")
        )
        Text(
            modifier = Modifier.constrainAs(episodeName) {
                top.linkTo(subtitle.bottom)
                start.linkTo(card.end, smallMargin)
                end.linkTo(favouriteButton.start, smallMargin)
                width = fillToConstraints
            },
            style = MaterialTheme.typography.bodyMedium,
            text = name ?: ""
        )

        FavouriteButton(
            isFavourite = item?.isFavourite ?: false,
            onClick = onFavouriteClick,
            modifier = Modifier.constrainAs(favouriteButton) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, smallMargin)
            }
        )
    }
    if (showAlertForNullId) {
        NullIdAlert()
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShimmerImage(
    modifier: Modifier = Modifier,
    animatedContentScope: AnimatedContentScope,
    coverUrl: String,
    id: Int?
) {
    val uiMode = LocalContext.current.resources.configuration.uiMode
    val darkMode by remember {
        derivedStateOf {
            (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        }
    }
    var isLoading by remember { mutableStateOf(true) }
    Card(
        modifier.sharedElement(
            rememberSharedContentState(key = id ?: coverUrl),
            animatedVisibilityScope = animatedContentScope
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        GlideSubcomposition(
            modifier = Modifier.shimmerLoadingAnimation(
                !isLoading,
                !darkMode
            ),
            model = coverUrl
        ) {
            when (state) {
                RequestState.Loading -> {
                    isLoading = true
                }

                RequestState.Failure, is RequestState.Success -> {
                    isLoading = false
                    Image(
                        if (state is RequestState.Failure) {
                            rememberVectorPainter(NoImage)
                        } else {
                            painter
                        },
                        contentDescription = "image",
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        colorFilter = null,
                        alpha = DefaultAlpha,
                    )
                }
            }
        }
    }

}

@Composable
fun NullIdAlert() {

}

@Composable
fun FavouriteButton(modifier: Modifier = Modifier, isFavourite: Boolean, onClick: () -> Unit) {
    Image(
        painter = if (isFavourite) rememberVectorPainter(Icons.Filled.Star) else painterResource(id = R.drawable.baseline_star_outline_24),
        if (isFavourite) "Favourite" else "Not Favourite",
        modifier = modifier.clickable { onClick() }
    )
}
