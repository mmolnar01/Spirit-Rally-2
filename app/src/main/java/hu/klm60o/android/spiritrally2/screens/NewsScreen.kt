package hu.klm60o.android.spiritrally2.screens

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.TimeUnit
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.presentation.news.NewsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.permissions.RequestCameraAndLocationPermissionDialog
import hu.klm60o.android.spiritrally2.permissions.RequestNotificationPermissionDialog
import hu.klm60o.android.spiritrally2.presentation.news.components.EmptyNewsListContent
import hu.klm60o.android.spiritrally2.presentation.news.components.NewsListContent
import hu.klm60o.android.spiritrally2.presentation.userdata.components.SetUserLocation
import hu.klm60o.android.spiritrally2.useful.showToast

@Composable
fun NewsScreenComposable(navController: NavController, viewModel: NewsViewModel = hiltViewModel(), innerPadding: PaddingValues) {
    val context = LocalContext.current
    val newsResponse by viewModel.newsState.collectAsStateWithLifecycle()

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }
    RequestCameraAndLocationPermissionDialog()

    var locationRequest by rememberSaveable {
        mutableStateOf<LocationRequest?>(null)
    }

    if (locationRequest != null) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            SetUserLocation(locationRequest!!)
        }
    }

    locationRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000).build()
    */
    /*Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) },
        topBar = { MyTopAppBar() }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            //val newsList = newsViewModel.itemList as List<News>
            //NewsList(newsViewModel.itemList.value)
            //Text("Ez itt a hírek képernyő")

            when (val newsResponse = newsResponse) {
                is Response.Idle -> {}
                is Response.Loading -> LoadingIndicator()
                is Response.Success -> newsResponse.data?.let { newsList ->
                    if (newsList.isEmpty()) {
                        EmptyNewsListContent(innerPadding)
                    } else {
                        NewsListContent(innerPadding, newsList)
                    }
                }

                is Response.Failure -> newsResponse.e?.message?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        showToast(context, errorMessage)
                    }
                }
            }
        }
    }*/
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        //val newsList = newsViewModel.itemList as List<News>
        //NewsList(newsViewModel.itemList.value)
        //Text("Ez itt a hírek képernyő")

        when (val newsResponse = newsResponse) {
            is Response.Idle -> {}
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> newsResponse.data?.let { newsList ->
                if (newsList.isEmpty()) {
                    EmptyNewsListContent(innerPadding)
                } else {
                    NewsListContent(innerPadding, newsList)
                }
            }

            is Response.Failure -> newsResponse.e?.message?.let { errorMessage ->
                LaunchedEffect(errorMessage) {
                    showToast(context, errorMessage)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        NewsScreenComposable(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        NewsScreenComposable(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}