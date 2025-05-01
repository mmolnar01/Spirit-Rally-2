package hu.klm60o.android.spiritrally2.presentation.news.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.klm60o.android.spiritrally2.domain.model.News
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme

@Composable
fun NewsListContent(
    innerPadding: PaddingValues,
    newsList: List<News>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(3.dp)
    ) {
        items(newsList, key = { news -> news.id!!}) { news ->
            NewsCard(news)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListContentPreview() {
    val news1 = News(1, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", false)
    val news2 = News(2, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", true)
    val news3 = News(3, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", false)
    val news4 = News(4, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", true)
    val news5 = News(5, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", false)
    val list: List<News> = listOf(news1, news2, news3, news4, news5)
    SpiritRally2Theme {
        NewsListContent(innerPadding = PaddingValues(), list)
    }
}