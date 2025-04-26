package hu.klm60o.android.spiritrally2.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.MapScreen
import hu.klm60o.android.spiritrally2.NewsScreen
import hu.klm60o.android.spiritrally2.ProfileScreen
import hu.klm60o.android.spiritrally2.ResultScreen
import hu.klm60o.android.spiritrally2.assets.QrCode

@Composable
fun MyBottomAppbarComposable(navController: NavController) {
    val context = LocalContext.current
    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier

                    .weight(1f)
            ) {
                IconButton(onClick = {
                    navController.navigate(NewsScreen)
                },) {
                    Icon(Icons.Filled.Home, contentDescription = "News screen")
                }
                Text(text = "Hírek",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
            ) {
                IconButton(onClick = {
                    navController.navigate(MapScreen)
                }) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Info screen")
                }
                Text(text = "Térkép",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
            ) {
                IconButton(onClick = {
                    navController.navigate(ResultScreen)
                }) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = "Results screen")
                }
                Text(text = "Eredmények",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
            ) {
                IconButton(onClick = {
                    navController.navigate(ProfileScreen)
                }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profile screen")
                }
                Text(text = "Profil",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
        }
        /*floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //checkCameraPermission(context)
                    //navController.navigate(NewsScreen)
                    //navController.navigate(ResultScreen)
                },
                containerColor = BottomAppBarDefaults.containerColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp, 15.dp, 15.dp,15.dp)
            ) {
                Icon(QrCode, contentDescription = "Read QR code")
            }
        }*/
}
    )
}

/*fun checkCameraPermission(context: Context) {
    if ((ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
        && (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    ) {
        (context as MainActivity).showCamera()
    }
    else if (shouldShowRequestPermissionRationale((context as MainActivity), Manifest.permission.CAMERA)) {
        showToast(context, "Kamera jogosultság szükséges")
        //context.requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        context.requestPermissionLauncher2.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    else if (shouldShowRequestPermissionRationale((context as MainActivity), Manifest.permission.ACCESS_FINE_LOCATION)) {
        showToast(context, "Helymeghatározás jogosultság szükséges")
        //context.requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        context.requestPermissionLauncher2.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    else {
        //context.requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        //context.requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        context.requestPermissionLauncher2.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}*/

@Preview(showBackground = true)
@Composable
fun MyBottomAppBarPreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        MyBottomAppbarComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomAppBarPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        MyBottomAppbarComposable(
            navController = rememberNavController()
        )
    }
}