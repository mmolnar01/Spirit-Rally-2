package hu.klm60o.android.spiritrally2.presentation.news.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.klm60o.android.spiritrally2.domain.model.News
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme

@Composable
fun NewsCard(
    news: News
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 5.dp, 8.dp, 5.dp),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                if (news.important == true) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Important News icon",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Not Important News Icon"
                    )
                }
            }
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(text = news.title.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                HorizontalDivider(
                    modifier = Modifier.padding(0.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(text = news.content.toString(), fontSize = 15.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    val news1 = News(1, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", false)
    SpiritRally2Theme {
        NewsCard(news1)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreviewImportant() {
    val news1 = News(1, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", true)
    SpiritRally2Theme {
        NewsCard(news1)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreviewDark() {
    val news1 = News(1, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", false)
    SpiritRally2Theme(darkTheme = true) {
        NewsCard(news1)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreviewImportantDark() {
    val news1 = News(1, "Hír címe", "Hír tartalma, lorem ipsum dolor sit atmet, rebarbara rebarbara", true)
    SpiritRally2Theme(darkTheme = true) {
        NewsCard(news1)
    }
}