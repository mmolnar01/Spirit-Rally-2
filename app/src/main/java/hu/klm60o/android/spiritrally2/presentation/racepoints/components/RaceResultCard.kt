package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.calculateAverageSpeedInKmH
import hu.klm60o.android.spiritrally2.useful.getDistanceFromGeoPoints
import hu.klm60o.android.spiritrally2.useful.getGeoPointsFromGpx
import hu.klm60o.android.spiritrally2.useful.round
import org.osmdroid.util.GeoPoint

@Composable
fun RaceResultCard(
    racePoints: List<Racepoint>
) {
    val context = LocalContext.current
    val achievedRacePoints = remember { mutableIntStateOf(0) }
    val totalRacepoints = remember { mutableIntStateOf(0) }
    totalRacepoints.intValue = racePoints.size - 2

    //val geoPoints = getGeoPointsFromGpx(context, "tabaliget.gpx")
    //val distance = getDistanceFromGeoPoints(geoPoints)

    //GeoPoint lista lekérdezése
    val geoPoints = remember { mutableStateListOf<GeoPoint>() }
    geoPoints.addAll(getGeoPointsFromGpx(context, "tabaliget.gpx"))

    //Távolság lekérdezése
    val distance = remember { mutableDoubleStateOf(0.0) }
    distance.doubleValue = getDistanceFromGeoPoints(geoPoints)

    //Átlagsebesség kiszámítása
    val averageSpeed = remember { mutableDoubleStateOf(0.0) }

    if (racePoints.first().timestamp != null && racePoints.last().timestamp != null) {
        averageSpeed.doubleValue = calculateAverageSpeedInKmH(racePoints.first().timestamp!!.seconds, racePoints.last().timestamp!!.seconds, distance.doubleValue)
            .round(2)
    }

    for (racePoint in racePoints) {
        if (racePoint.timestamp != null && racePoint.id != 1 && racePoint.id != racePoints.size) {
            achievedRacePoints.intValue++
        }
    }

    //Eredmény szövegek létrehozása
    val averageSpeedText = remember { mutableStateOf("") }
    val racePointText = remember { mutableStateOf("") }
    averageSpeedText.value = "${averageSpeed.doubleValue} km/h"
    racePointText.value = "${achievedRacePoints.intValue} / ${totalRacepoints.intValue}"

    Column(

    ) {
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("Teljesített ellenőrző pontok")
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                OutlinedTextField(
                    value = racePointText.value,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .width(100.dp),
                    label = {
                        Text(text = "")
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("Átlagsebesség")
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                OutlinedTextField(
                    value = averageSpeedText.value,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .width(100.dp),
                    label = {
                        Text(text = "")
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun RaceResultCardPreview() {
    val racePoint1 = Racepoint(1, com.google.firebase.firestore.GeoPoint(1.0, 1.0), Timestamp(100000, 50000))
    val racePoint2 = Racepoint(2, com.google.firebase.firestore.GeoPoint(1.0, 1.0), Timestamp(100001, 50001))
    val racePoint3 = Racepoint(3, com.google.firebase.firestore.GeoPoint(1.0, 1.0))
    val racePoint4 = Racepoint(4, com.google.firebase.firestore.GeoPoint(1.0, 1.0))
    val racePoint5 = Racepoint(5, com.google.firebase.firestore.GeoPoint(1.0, 1.0))
    val racePoint6 = Racepoint(6, com.google.firebase.firestore.GeoPoint(1.0, 1.0))
    val racePoint7 = Racepoint(7, com.google.firebase.firestore.GeoPoint(1.0, 1.0), Timestamp(100005, 50005))
    val racePointsListTest: List<Racepoint> = listOf(racePoint1, racePoint2, racePoint3, racePoint4, racePoint5, racePoint6, racePoint7)
    SpiritRally2Theme {
        RaceResultCard(racePointsListTest)
    }
}