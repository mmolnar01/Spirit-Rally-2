package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.GeoPoint
import hu.klm60o.android.spiritrally2.presentation.racepoints.RacepointsViewModel
import java.util.Calendar

@Composable
fun ResultScreenComposable(navController: NavController, viewModel: RacepointsViewModel = hiltViewModel()) {
    Scaffold(bottomBar = { MyBottomAppbarComposable(navController)}) {
        innerPadding ->
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Ez itt az eredmények képernyő")
            /*if (viewModel.racePoints != null) {
                RacePointListComposable(viewModel.racePoints!!, viewModel.distance!!, viewModel.achievedRacePoints)
            }*/
            //RacePointListComposable(viewModel.racePoints!!)
        }
    }
}

/*@Composable
fun ResultScreenComposable(navController: NavController) {
    /*if (currentUser != null) {
        getUserDataFromFirestore(
            currentUser = currentUser,
            viewModel = viewModel,
            context = LocalContext.current
        )
    }*/
    //viewModel.racePoints?.let { viewModel.test.addAll(it) }
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) }
    ) {
            innerPadding ->
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            //Text("Ez itt az eredmények képernyő")
            if (viewModel.racePoints != null) {
                RacePointListComposable(viewModel.racePoints!!, viewModel.distance!!, viewModel.achievedRacePoints)
            }
            //RacePointListComposable(viewModel.racePoints!!)
        }

    }
}*/

/*@Composable
fun RacePointListComposable(racePoints: List<RacePoint>, distance: Int, achieved: Int) {
    /*val test = remember {
        mutableStateOf(racePoints)
    }*/
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(racePoints, key = { racePoint -> racePoint.id!! }) { racePoint ->
            RacePointComposable(racePoint, racePoints.size)
        }
        item {
            Divider(
                thickness = 2.dp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("A verseny távolsága: " + distance.toString() + " km")
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Ellenörző pontok: " + achieved.toString() + " / " + (racePoints.size - 2).toString())
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (racePoints.last().timeStamp != null && racePoints.first().timeStamp != null) {
                        val endTimeStamp = racePoints.last().timeStamp!!.seconds
                        val startTimeStamp = racePoints.first().timeStamp!!.seconds
                        val timeTaken = ((endTimeStamp - startTimeStamp).toDouble()) / 3600
                        Text("Átlagsebesség: " + (distance / timeTaken).round(2).toString() + " km/h")
                    } else {
                        Text("Átlagsebesség: ")
                    }

                    /*if (test.value.last().timeStamp != null && test.value.first().timeStamp != null) {
                        val endTimeStamp = test.value.last().timeStamp!!.seconds
                        val startTimeStamp = test.value.first().timeStamp!!.seconds
                        val timeTaken = ((endTimeStamp - startTimeStamp).toDouble()) / 3600
                        Text("Átlagsebesség: " + (distance / timeTaken).round(2).toString() + " km/h")
                    } else {
                        Text("Átlagsebesség: ")
                    }*/
                }
            }
        }
    }
}*/

/*@Composable
fun RacePointComposable(racePoint: RacePoint, size: Int) {
    val racePointCalendar = Calendar.getInstance()
    val seconds = racePoint.timeStamp?.seconds
    var timeText = "-"
    if (seconds != null) {
        racePointCalendar.timeInMillis = seconds * 1000

        //Lekérdezzük az időt órában és percben
        if (racePointCalendar.get(Calendar.MINUTE) < 10) {
            timeText = racePointCalendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + "0" + racePointCalendar.get(
                Calendar.MINUTE).toString()
        } else {
            timeText = racePointCalendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + racePointCalendar.get(
                Calendar.MINUTE).toString()
        }
    }
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (racePoint.id == 1) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "Start Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "Rajt"
                )
            }

        } else if (racePoint.id == size) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "End Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "Cél"
                )
            }

        } else {
            val racePointNumber = racePoint.id?.minus(1)
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "Intermediate Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "$racePointNumber. ellenörző pont"
                )
            }

        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            OutlinedTextField(
                value = timeText,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .width(100.dp),
                label = {
                    Text("Időpont")
                }
            )
        }


    }
}*/

/*@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    val racePoint1 = RacePoint(1, GeoPoint(1.0, 1.0))
    val racePoint2 = RacePoint(2, GeoPoint(1.0, 1.0))
    val racePoint3 = RacePoint(3, GeoPoint(1.0, 1.0))
    val racePoint4 = RacePoint(4, GeoPoint(1.0, 1.0))
    val racePoint5 = RacePoint(5, GeoPoint(1.0, 1.0))
    val racePoint6 = RacePoint(6, GeoPoint(1.0, 1.0))
    val racePoint7 = RacePoint(7, GeoPoint(1.0, 1.0))
    val racePointsListTest: List<RacePoint> = listOf(racePoint1, racePoint2, racePoint3, racePoint4, racePoint5, racePoint6, racePoint7)
    val viewModelTest = UserViewModel()
    viewModelTest.racePoints = racePointsListTest
    viewModelTest.distance = 100
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        ResultScreenComposable(
            navController = rememberNavController()
        )
    }
}*/

@Preview(showBackground = true)
@Composable
fun ResultPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        ResultScreenComposable(
            navController = rememberNavController()
        )
    }
}