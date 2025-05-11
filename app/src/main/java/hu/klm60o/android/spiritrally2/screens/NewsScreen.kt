package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.presentation.news.components.EmptyNewsListContent
import hu.klm60o.android.spiritrally2.presentation.news.components.NewsListContent
import hu.klm60o.android.spiritrally2.useful.showToast

@Composable
fun NewsScreenComposable(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val newsResponse by viewModel.newsState.collectAsStateWithLifecycle()

    Scaffold(
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

    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        NewsScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        NewsScreenComposable(
            navController = rememberNavController()
        )
    }
}