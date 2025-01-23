package com.uticodes.mivaandroidtest.view.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.uticodes.mivaandroidtest.data.models.Subject
import com.uticodes.mivaandroidtest.ui.theme.TextColor

@Composable
fun Subjects(
    modifier: Modifier = Modifier,
    subjects: List<Subject>,
    onSubjectClick: (subject: Subject) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(subjects) { subject ->
            SubjectItem(subject) { onSubjectClick(subject) }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SubjectItem(subject: Subject, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .semantics { testTagsAsResourceId = true }
            .testTag("subject"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(subject.icon),
            contentDescription = "Subject Icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("subjectIcon")
        )
        Text(
            subject.title,
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("subjectTitle"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextColor,
            ),

            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
