package com.uticodes.mivaandroidtest.view.subject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.models.Lesson

@Composable
fun ChaptersView(
    modifier: Modifier = Modifier,
    chapters: List<Chapter>,
    onLessonSelected: (lesson: Lesson) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        chapters.forEachIndexed { index, chapter ->
            ChapterCard(chapter, index) { lesson ->
                onLessonSelected(lesson)
            }
        }
    }
}

@Composable
fun ChapterCard(chapter: Chapter, index: Int, onSelectedLesson: (title: Lesson) -> Unit) {

    var lessonsVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .padding(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { lessonsVisible = !lessonsVisible },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.ic_chapter), "Chapter")
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.chapter, index + 1))
                        Text(
                            modifier = Modifier
                                .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            text = stringResource(R.string.lessons, chapter.lessons.size),
                            fontSize = 12.sp
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = chapter.title,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.W500
                        )

                        Icon(
                            painter = painterResource(
                                if (lessonsVisible) R.drawable.ic_drop_up else R.drawable.ic_dropdown
                            ),
                            contentDescription = if (lessonsVisible) "Drop up" else "Dropdown"
                        )
                    }
                }
            }

            AnimatedVisibility(visible = lessonsVisible) {
                LessonList(lessons = chapter.lessons) { onSelectedLesson(it) }
            }
        }
    }
}

@Composable
fun LessonList(lessons: List<Lesson>, onSelectLesson: (lesson: Lesson) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        lessons.forEach {
            LessonTile(lesson = it) {
                onSelectLesson(it)
            }
        }
    }
}

@Composable
fun LessonTile(lesson: Lesson, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = "Play",
            tint = Color.Unspecified
        )
        Text(lesson.title)
    }
}
