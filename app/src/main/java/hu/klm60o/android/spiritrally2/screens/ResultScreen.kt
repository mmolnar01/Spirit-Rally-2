package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.presentation.racepoints.RacepointsViewModel
import hu.klm60o.android.spiritrally2.presentation.racepoints.components.AddRacepointFloatingActionButton
import hu.klm60o.android.spiritrally2.presentation.racepoints.components.EmptyRacepointListContent
import hu.klm60o.android.spiritrally2.presentation.racepoints.components.RacepointListContent
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.showToast
import java.util.Calendar


@Composable
fun ResultScreenComposable(navController: NavController, viewModel: RacepointsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val racepointsResponse by viewModel.racepointsState.collectAsStateWithLifecycle()
    val editRacepointResponse by viewModel.editRacepointState.collectAsStateWithLifecycle()

    val localRacePointsList = remember { mutableStateListOf<Racepoint>() }

    Scaffold(
        floatingActionButton = {
            AddRacepointFloatingActionButton(
                onEditRacepoint = viewModel::editRacepoint,
                racepointList = localRacePointsList
            )
        }
    ) { innerPadding ->
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            //Text("Ez itt az eredmények képernyő")

            when(val racepointsResponse = racepointsResponse) {
                is Response.Idle -> {}
                is Response.Loading -> LoadingIndicator()
                is Response.Success -> racepointsResponse.data?.let { racepointsList ->
                    if (racepointsList.isEmpty()) {
                        EmptyRacepointListContent(innerPadding = innerPadding)
                    } else {
                        RacepointListContent(innerPadding = innerPadding, racePointList = racepointsList)
                        //localRacePointsList = racepointsList
                        localRacePointsList.clear()
                        localRacePointsList.addAll(racepointsList)
                    }
                }
                is Response.Failure -> racepointsResponse.e?.message?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        showToast(context, errorMessage)
                    }
                }
            }

            when(val editRacepointResponse = editRacepointResponse) {
                is Response.Idle -> {}
                is Response.Loading -> LoadingIndicator()
                is Response.Success -> LaunchedEffect(Unit) {
                    showToast(context, "Ellenörző pont frissítve")
                    viewModel.resetEditRacepointState()
                }
                is Response.Failure -> editRacepointResponse.e?.message?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        showToast(context, errorMessage)
                        viewModel.resetEditRacepointState()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    SpiritRally2Theme {
        ResultScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        ResultScreenComposable(
            navController = rememberNavController()
        )
    }
}