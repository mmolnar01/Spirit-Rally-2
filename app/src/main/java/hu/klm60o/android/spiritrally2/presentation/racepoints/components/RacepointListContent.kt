package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme

@Composable
fun RacepointListContent(
    racePointList: List<Racepoint>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(3.dp)
    ) {
        items(racePointList, key = { racePoint -> racePoint.id!! }) { racePoint ->
            RacepointCard(racePoint, racePointList.size)
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
        item {
            RaceResultCard(racePointList)
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun RacepointListContentPreview() {
    val racePoint1 = Racepoint(1, GeoPoint(1.0, 1.0), Timestamp(100000, 50000))
    val racePoint2 = Racepoint(2, GeoPoint(1.0, 1.0))
    val racePoint3 = Racepoint(3, GeoPoint(1.0, 1.0))
    val racePoint4 = Racepoint(4, GeoPoint(1.0, 1.0))
    val racePoint5 = Racepoint(5, GeoPoint(1.0, 1.0))
    val racePoint6 = Racepoint(6, GeoPoint(1.0, 1.0))
    val racePoint7 = Racepoint(7, GeoPoint(1.0, 1.0))
    val racePointsListTest: List<Racepoint> = listOf(racePoint1, racePoint2, racePoint3, racePoint4, racePoint5, racePoint6, racePoint7)
    SpiritRally2Theme {
        RacepointListContent(racePointsListTest)
    }
}