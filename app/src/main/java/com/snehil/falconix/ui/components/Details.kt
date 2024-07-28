package com.snehil.falconix.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.material.chip.ChipGroup
import com.snehil.falconix.R
import com.snehil.falconix.api.model.LaunchWithRocket
import com.snehil.falconix.api.model.Payload
import com.snehil.falconix.api.model.RocketWithPayload
import com.snehil.falconix.ui.theme.LocalCellSizes
import com.snehil.falconix.ui.theme.LocalSpacings
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun Details(id: String?, navHostController: NavHostController) {
    if (id != null) {
        //Display detailed information about the selected launch, including mission name,
        //launch date, rocket details, payload details, launch site, and links to media and
        //articles.
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            var launch by remember { mutableStateOf<LaunchWithRocket?>(null) }
            var showProgress by remember { mutableStateOf(true) }
            LaunchedEffect(id) {
                launch = detailsViewModel.getLaunch(id)
                showProgress = false
            }
            if (showProgress) {
                CircularProgressIndicator()
            } else if (launch != null) {
                launch?.let { launch1 ->
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                navigationIcon = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        modifier = Modifier.clickable {
                                            navHostController.popBackStack()
                                        }, contentDescription = "back"
                                    )
                                },
                                title = { Text(launch1.launchData.missionName ?: stringResource(R.string.not_found)) }

                            )
                        }
                    ) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(
                                    LocalSpacings.current.xs
                                )
                            ) {
                                item {
                                    LaunchDetails(launch1)
                                }
                                item {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            LocalSpacings.current.xs
                                        )
                                    ) {
                                        launch1.launchData.links?.flickrImages?.let { image ->
                                            items(image) {
                                                GlideImage(model = it, contentDescription = null, modifier = Modifier.height(
                                                    LocalCellSizes.current.xxxl))
                                            }
                                        }
                                    }
                                }
                                items(launch1.rockets) {
                                    RocketDetails(it)
                                }
                                item {
                                    FlowRow {
                                        LinkChip(launch1.launchData.links?.missionPatch ?: "")
                                        LinkChip(launch1.launchData.links?.missionPatchSmall ?: "")
                                        LinkChip(launch1.launchData.links?.redditCampaign ?: "")
                                        LinkChip(launch1.launchData.links?.redditMedia ?: "")
                                        LinkChip(launch1.launchData.links?.redditLaunch ?: "")
                                        LinkChip(launch1.launchData.links?.redditRecovery ?: "")
                                        LinkChip(launch1.launchData.links?.presskit ?: "")
                                        LinkChip(launch1.launchData.links?.articleLink ?: "")
                                        LinkChip(launch1.launchData.links?.wikipedia ?: "")
                                        LinkChip(launch1.launchData.links?.videoLink ?: "")
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                NoContent()
            }
        }
    } else {
        NoContent()
    }
}

@Composable
fun RocketDetails(rocketWithPayload: RocketWithPayload) {
    HorizontalDivider(modifier = Modifier.padding(vertical = LocalSpacings.current.sm))
    rocketWithPayload.rocket.rocketName?.let { Text(stringResource(R.string.rocket_name, it)) }
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacings.current.xs
        )
    ) {
        items(rocketWithPayload.payloads) {
            PayloadDetails(it)
        }
    }
}

@Composable
fun PayloadDetails(payload: Payload) {
    Card (Modifier.padding(LocalSpacings.current.sm)) {
        Column(Modifier.padding(LocalSpacings.current.xs).apply {
            if (payload.reused == true) {
                background(color = Color.Green)
            }
        }) {
            Text(stringResource(R.string.payload_type, payload.payloadType ?: "null"))
            Text(stringResource(R.string.payload_mass_kg,
                humanReadableMass(payload.payloadMassKg?.times(1000) ?: 0.0)
            ))
            Text(stringResource(R.string.payload_mass_lbs,
                humanReadableMassInLbs(payload.payloadMassLbs ?: 0.0)
            ))
            Text(stringResource(R.string.payload_orbit, payload.orbit?: "null"))
            Text(stringResource(R.string.customers, payload.customers))
            Text(stringResource(R.string.nationality, payload.nationality ?: "null"))
            Text(stringResource(R.string.manufacturer, payload.manufacturer ?: "null"))
        }
    }
}
fun humanReadableMass(massInGrams: Double): String {
    val units = listOf("grams", "kilograms", "metric tons")
    val values = listOf(1.0, 1_000.0, 1_000_000.0)

    var value = massInGrams
    var unit = units[0]

    for (i in values.indices.reversed()) {
        if (massInGrams >= values[i]) {
            value = massInGrams / values[i]
            unit = units[i]
            break
        }
    }

    return "%.2f %s".format(value, unit)
}

fun humanReadableMassInLbs(massInLbs: Double): String {
    val units = listOf("pounds", "ounces", "tons")
    val values = listOf(1.0, 1 / 16.0, 2000.0)

    var value = massInLbs
    var unit = units[0]

    for (i in values.indices.reversed()) {
        if (massInLbs >= values[i]) {
            value = massInLbs / values[i]
            unit = units[i]
            break
        }
    }

    return "%.2f %s".format(value, unit)
}


@Composable
fun LaunchDetails(launchWithRocket: LaunchWithRocket) {
    Text(stringResource(R.string.mission, launchWithRocket.launchData.missionName ?: "null"))
    Text(stringResource(R.string.launch_date, launchWithRocket.launchData.launchDateLocal ?: "null"))
    Text(stringResource(R.string.launch_site, launchWithRocket.launchData.launchSite?.siteName ?: "null"))
}

@Composable
fun LinkChip(url: String) {
    if (url.isNotBlank()) {
        val httpUrl = url.toHttpUrlOrNull()
        val context = LocalContext.current
        AssistChip(
            onClick = {
                openUrl(url, context)
            },
            label = {
                Text(httpUrl?.host ?: "")
            }
        )
    }
}

fun openUrl(httpUrl: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, httpUrl.toUri())
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, context.getString(R.string.no_browser_found), Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun NoContent() {
    Scaffold {
        Box(Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_content))
        }
    }

}
