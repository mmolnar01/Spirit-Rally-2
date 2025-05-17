package hu.klm60o.android.spiritrally2.presentation.userdata.components

import android.Manifest
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import hu.klm60o.android.spiritrally2.assets.QrCode

@Composable
fun AddUserDataFloatingActionButton() {
    FloatingActionButton(
        onClick = {},
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp, 15.dp, 15.dp,15.dp)
    ) {
        Icon(QrCode, contentDescription = "Read QR code")
    }
}