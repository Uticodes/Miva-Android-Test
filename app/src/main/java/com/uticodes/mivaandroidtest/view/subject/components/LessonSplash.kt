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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.data.models.Subject
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Destination(
    navArgsDelegate = LessonSplashScreenNavArgs::class
)
@Composable
fun LessonSplashScreen(
    navigator: DestinationsNavigator
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            progress += 0.01f
            delay(30)
        }
        // Navigate to the next screen when progress completes
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF6E40)), // Replace with your background color
        contentAlignment = Alignment.Center // Center everything in the Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp), // Adjust size for the image and progress
                contentAlignment = Alignment.Center
            ) {
                // Circular Progress Indicator
                CircularProgressIndicator(
                    progress = {
                        progress // Progress between 0.0 and 1.0
                    },
                    modifier = Modifier.matchParentSize(), // Match the size of the Box
                    color = Color.White, // Progress bar color
                    strokeWidth = 6.dp, // Thickness of the progress bar
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
                text = "Chapter 1",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Properties of Plane shapes",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


data class LessonSplashScreenNavArgs(
    val subject: Subject
)