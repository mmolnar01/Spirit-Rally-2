package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.presentation.news.NewsViewModel

@Composable
fun NewsScreenComposable(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {
    val navController = navController
    //al viewModel = viewModel
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) }
    ) {
            innerPadding ->
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            //val newsList = newsViewModel.itemList as List<News>
            //NewsList(newsViewModel.itemList.value)
            Text("Ez itt a hírek képernyő")
        }

    }
    /*Surface {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)) {
            Text(text = "Ez itt a hírek képernyő", fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center
            )
        }
        MyBottomAppbarComposable()
    }*/
}

/*@Composable
fun NewsList(itemList: List<News>) {
    LazyColumn {
        items(itemList, key = { item -> item.id }) { item ->
            ListItem(
                headlineContent = {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    )
                },
                supportingContent = {
                    Text(text = item.content,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    )
                },
                leadingContent = {
                    if (item.important) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "NewsIcon",
                            tint = Color.Red
                        )
                    }
                    else {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "NewsIcon"
                        )
                    }

                },
                overlineContent = {
                    if (item.important) {
                        Text(text = "FIYGELMEZTETÉS",
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                    }
                    else {
                        Text(text = "INFORMÁCIÓ",
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                    }
                }
            )
            Divider()
        }
    }
}*/

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