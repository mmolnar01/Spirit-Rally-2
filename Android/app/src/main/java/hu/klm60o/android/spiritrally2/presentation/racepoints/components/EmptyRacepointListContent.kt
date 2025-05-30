package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyRacepointListContent() {
    Box(
        modifier = Modifier.fillMaxSize().padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Jelenleg nincs aktív rally",
            fontSize = 20.sp
        )
    }
}