package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import hu.klm60o.android.spiritrally2.assets.IconFlag
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import java.text.SimpleDateFormat

@Composable
fun RacepointCard(
    racepoint: Racepoint,
    size: Int
) {
    val formatter = SimpleDateFormat("HH:mm", java.util.Locale.GERMAN)
    var timeText = "-"
    if (racepoint.timestamp != null) {
        val date = racepoint.timestamp!!.toDate()
        timeText = formatter.format(date)
    }
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (racepoint.id == 1) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    IconFlag, contentDescription = "Start point icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("Rajt")
            }
        } else if (racepoint.id == size) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    IconFlag, contentDescription = "End point icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("Cél")
            }
        } else {
            val racepointNumber = racepoint.id?.minus(1)
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "Intermediate point icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("$racepointNumber. ellenőrző pont")
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
                    .width(120.dp),
                label = {
                    Text("Időpont")
                }
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun RacepointCardPreview() {
    val racePoint1 = Racepoint(1, GeoPoint(1.0, 1.0), Timestamp(100000, 50000))
    SpiritRally2Theme {
        RacepointCard(racePoint1, 1)
    }
}