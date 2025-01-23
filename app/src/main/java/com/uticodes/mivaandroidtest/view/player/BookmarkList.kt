package com.uticodes.mivaandroidtest.view.player

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.ui.theme.TextColor

@Composable
fun BookmarkList(
    modifier: Modifier = Modifier,
    bookmarks: List<Bookmark>,
    onSelectBookmark: (bookmark: Bookmark) -> Unit,
    onDeleteBookmark: (bookmark: Bookmark) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.bookmarks),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = TextColor
                ),
            )
        }
        item {
            HorizontalDivider()
        }
        items(bookmarks) { bookmark ->
            BookmarkItem(bookmark = bookmark, onClick = { onSelectBookmark(bookmark) }) {
                onDeleteBookmark(bookmark)
            }
        }
    }
}

@Composable
fun BookmarkItem(bookmark: Bookmark, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    DateUtils.formatElapsedTime(bookmark.timestamp / 1000),
                    color = TextColor
                )
                Text(
                    text = bookmark.note,
                    color = TextColor
                )
            }

            Icon(
                modifier = Modifier.clickable {
                    onDelete()
                },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
            )
        }
    }
}
