package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.navigation.NavigationItem

@Composable
fun MyBottomAppbarComposable(navController: NavController, navigationItems: List<NavigationItem>) {
    val context = LocalContext.current
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    /*BottomAppBar(
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
    })*/
    NavigationBar() {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        item.title/*,
                        color = if (index == selectedNavigationIndex.intValue) {
                            //Color.Black
                        } else {
                            //Color.Gray
                        }*/
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }

}

/*@Preview(showBackground = true)
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
}*/