package com.uticodes.mivaandroidtest.view.subject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.utils.LessonsWrapper
import com.uticodes.mivaandroidtest.view.destinations.LessonPlayerScreenDestination
import com.uticodes.mivaandroidtest.view.destinations.LessonSplashScreenDestination
import kotlinx.coroutines.delay

@Destination
@Composable
fun LessonSplashScreen(
    navigator: DestinationsNavigator,
    lessons: LessonsWrapper,
    index: Int
) {

    var progress by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    val lesson = lessons.lessons[index]

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            progress += 0.01f
            delay(30)
        }

        navigator.navigate(
            LessonPlayerScreenDestination(
                lessons = lessons,
                index = index
            ),
            onlyIfResumed = true,
            builder = {
                popUpTo(LessonSplashScreenDestination.route) { inclusive = true }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF6E40)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lesson_splash),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = {
                        progress
                    },
                    modifier = Modifier.matchParentSize(),
                    color = Color.White,
                    strokeWidth = 6.dp,
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_chapter),
                    contentDescription = "Center Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Lesson ${index+1}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = lesson.title,
                modifier = Modifier.padding(horizontal = 80.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
