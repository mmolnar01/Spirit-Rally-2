package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.klm60o.android.spiritrally2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    Surface(shadowElevation = 10.dp, tonalElevation = 5.dp) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

            ),
            title = {
                Image(painter = painterResource(id = R.drawable.spirit_rally_transparent),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyTopAppBarPreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        MyTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun MyTopAppBarPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        MyTopAppBar()
    }
}
