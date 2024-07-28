package com.snehil.falconix.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.snehil.falconix.R
import com.snehil.falconix.api.model.LaunchWithRocket
import com.snehil.falconix.api.model.Payload
import com.snehil.falconix.api.model.RocketWithPayload
import com.snehil.falconix.ui.theme.LocalSpacings

@OptIn(ExperimentalMaterial3Api::class)
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
                            Column(
                                modifier = Modifier.padding(LocalSpacings.current.xs),
                                verticalArrangement = Arrangement.spacedBy(
                                    LocalSpacings.current.xs
                                )
                            ) {
                                LaunchDetails(launch1)
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(
                                        LocalSpacings.current.xs
                                    )
                                ) {
                                    items(launch1.rockets) {
                                        RocketDetails(it)
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
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = LocalSpacings.current.sm))
        }
        items(rocketWithPayload.payloads) {
            PayloadDetails(it)
        }
    }
}

@Composable
fun PayloadDetails(payload: Payload) {
    Card (Modifier.padding(LocalSpacings.current.xs)) {
        Column(Modifier.padding(LocalSpacings.current.xs)) {
            Text(stringResource(R.string.payload_type, payload.payloadType ?: "null"))
            Text(stringResource(R.string.payload_mass_kg,
                humanReadableMass(payload.payloadMassKg?.times(1000) ?: 0.0)
            ))
            Text(stringResource(R.string.payload_mass_lbs,
                humanReadableMassInLbs(payload.payloadMassLbs ?: 0.0)
            ))
            Text(stringResource(R.string.payload_orbit, payload.orbit?: "null"))
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
fun NoContent() {
    Scaffold {
        Box(Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_content))
        }
    }

}
