package hu.klm60o.android.spiritrally2.screens

import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.utsman.osmandcompose.CameraProperty
import com.utsman.osmandcompose.CameraState
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberMarkerState
import hu.klm60o.android.spiritrally2.R
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.presentation.racepoints.RacepointsViewModel
import hu.klm60o.android.spiritrally2.presentation.userdata.UserDataViewModel
import hu.klm60o.android.spiritrally2.useful.getGeoPointsFromGpx
import hu.klm60o.android.spiritrally2.useful.showToast
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreenComposable(
    racepointsViewModel: RacepointsViewModel = hiltViewModel(),
    userDataViewModel: UserDataViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val racepointsResponse by racepointsViewModel.racepointsState.collectAsStateWithLifecycle()
    val userDataListResponse by userDataViewModel.userDataListState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val geoPoints = getGeoPointsFromGpx(context, "tabaliget.gpx")


    val redIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context , R.drawable.map_marker_red))
    }

    val greenIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.map_marker_green))
    }

    val carIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.car_icon))
    }

    val motorcycleIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.motorcycle_icon))
    }

    val atvIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.atv_icon))
    }

    val minimalIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.minimal_icon))
    }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(0.dp)
    ) {

        var cameraState by remember {
            mutableStateOf(
                CameraState(
                    CameraProperty(
                        geoPoint = GeoPoint(47.3645399756769, 18.863085071980695),
                        zoom = 12.0
                    )
                )
            )
        }

        LaunchedEffect(cameraState.zoom) {
            val zoom = cameraState.zoom
            val geoPoint = cameraState.geoPoint
            cameraState = CameraState(
                CameraProperty(
                    geoPoint = geoPoint,
                    zoom = zoom
                )
            )
        }

        // add node
        OpenStreetMap(
            modifier = Modifier.fillMaxSize(),
            cameraState = cameraState
        ) {
            when(val racepointsResponse = racepointsResponse) {
                is Response.Idle -> {}
                is Response.Loading -> {}
                is Response.Success -> racepointsResponse.data?.let { racepointsList ->
                    if (racepointsList.isEmpty()) {
                        //EmptyRacepointListContent(innerPadding = innerPadding)
                    } else {
                        racepointsList.forEach { racepoint ->
                            if (racepoint.timestamp != null) {
                                Marker(
                                    state = rememberMarkerState(geoPoint = GeoPoint(racepoint.location!!.latitude, racepoint.location!!.longitude), rotation = 90f),
                                    icon = greenIcon
                                )
                            } else {
                                Marker(
                                    state = rememberMarkerState(geoPoint = GeoPoint(racepoint.location!!.latitude, racepoint.location!!.longitude)),
                                    icon = redIcon
                                )
                            }
                        }
                    }
                }
                is Response.Failure -> racepointsResponse.e?.message?.let { errorMessage ->
                    showToast(context, errorMessage)
                    Log.e(TAG, "MapScreen racepointsResponse Hiba: $errorMessage")
                }
            }

            when(val userDataListResponse = userDataListResponse) {
                is Response.Idle -> {}
                is Response.Loading -> {}
                is Response.Success -> userDataListResponse.data?.let { userDataList ->
                    if (userDataList.isEmpty()) {

                    } else {
                        userDataList.forEach { userData ->
                            if (userData.location != null) {
                                when (userData.category) {
                                    1 -> Marker(
                                        state = rememberMarkerState(geoPoint = GeoPoint(userData.location!!.latitude, userData.location!!.longitude)),
                                        icon = carIcon,
                                        title = "Rajtszám: " + userData.number.toString(),
                                        snippet = "1: Autó"
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .background(color = Color.Gray, shape = RoundedCornerShape(7.dp)),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                            ) {
                                                Text(text = "x", modifier = Modifier.align(Alignment.TopEnd))
                                                Column(
                                                    modifier = Modifier.align(Alignment.Center)
                                                        .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(it.title)
                                                    Text(it.snippet)
                                                }
                                            }
                                        }
                                    }
                                    2 -> Marker(
                                        state = rememberMarkerState(geoPoint = GeoPoint(userData.location!!.latitude, userData.location!!.longitude)),
                                        icon = motorcycleIcon,
                                        title = "Rajtszám: " + userData.number.toString(),
                                        snippet = "2: Motor"
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .background(color = Color.Gray, shape = RoundedCornerShape(7.dp)),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                            ) {
                                                Text(text = "x", modifier = Modifier.align(Alignment.TopEnd))
                                                Column(
                                                    modifier = Modifier.align(Alignment.Center)
                                                        .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(it.title)
                                                    Text(it.snippet)
                                                }
                                            }
                                        }
                                    }
                                    3 -> Marker(
                                        state = rememberMarkerState(geoPoint = GeoPoint(userData.location!!.latitude, userData.location!!.longitude)),
                                        icon = atvIcon,
                                        title = "Rajtszám: " + userData.number.toString(),
                                        snippet = "3: ATV"
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .background(color = Color.Gray, shape = RoundedCornerShape(7.dp)),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                            ) {
                                                Text(text = "x", modifier = Modifier.align(Alignment.TopEnd))
                                                Column(
                                                    modifier = Modifier.align(Alignment.Center)
                                                        .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(it.title)
                                                    Text(it.snippet)
                                                }
                                            }
                                        }
                                    }
                                    4 -> Marker(
                                        state = rememberMarkerState(geoPoint = GeoPoint(userData.location!!.latitude, userData.location!!.longitude)),
                                        icon = minimalIcon,
                                        title = "Rajtszám: " + userData.number.toString(),
                                        snippet = "4: Minimál"
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .background(color = Color.Gray, shape = RoundedCornerShape(7.dp)),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                            ) {
                                                Text(text = "x", modifier = Modifier.align(Alignment.TopEnd))
                                                Column(
                                                    modifier = Modifier.align(Alignment.Center)
                                                        .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(it.title)
                                                    Text(it.snippet)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                is Response.Failure -> userDataListResponse.e?.message?.let { errorMessage ->
                    showToast(context, errorMessage)
                    Log.e(TAG, "MapScreen racepointsResponse Hiba: $errorMessage")
                }
            }

            com.utsman.osmandcompose.Polyline(
                geoPoints = geoPoints,
                color = Color.Blue
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun MapPreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        MapScreenComposable(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        MapScreenComposable(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}*/