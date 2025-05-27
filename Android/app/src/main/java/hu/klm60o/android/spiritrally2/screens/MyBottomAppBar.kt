package hu.klm60o.android.spiritrally2.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import hu.klm60o.android.spiritrally2.navigation.NavigationItem

@Composable
fun MyBottomAppbarComposable(navController: NavController, navigationItems: List<NavigationItem>) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar() {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route) { popUpTo(0) }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        item.title
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