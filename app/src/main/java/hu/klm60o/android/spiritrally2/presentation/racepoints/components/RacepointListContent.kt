package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue

@Composable
fun RacepointListContent(
    innerPadding: PaddingValues,
    racepointList: List<Racepoint>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        items(racepointList, key = { racePoint -> racePoint.id!! }) { racePoint ->
            Text(racePoint.id.toString())
        }
    }
}