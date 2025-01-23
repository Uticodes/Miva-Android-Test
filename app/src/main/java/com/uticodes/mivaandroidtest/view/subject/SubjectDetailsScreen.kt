package com.uticodes.mivaandroidtest.view.subject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.ui.theme.TextColor
import com.uticodes.mivaandroidtest.utils.LessonsWrapper
import com.uticodes.mivaandroidtest.utils.UIState
import com.uticodes.mivaandroidtest.view.destinations.LessonPlayerScreenDestination
import com.uticodes.mivaandroidtest.view.homeScreen.component.SearchBarView
import com.uticodes.mivaandroidtest.view.homeScreen.component.TitleText
import com.uticodes.mivaandroidtest.view.subject.components.TopBar
import com.uticodes.mivaandroidtest.view.viewModel.SubjectViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Destination(
    navArgsDelegate = SubjectDetailsScreenNavArgs::class
)
@Composable
fun SubjectDetailsScreen(navigator: DestinationsNavigator) {
    val viewModel: SubjectViewModel = hiltViewModel()
    val subject = viewModel.subject

    var searchQuery by remember { viewModel.searchQuery }
    val uiState by remember { viewModel.uiState }
    val chapters = remember { viewModel.chapters }
    val resumeLearningProgress by remember { viewModel.resumeLearningProgress }

    LaunchedEffect(Unit) {
        viewModel.loadChapters()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { TopBar(navigator) }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.ic_home_design_splash),
                contentDescription = "Home Splash Background"
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                TitleText(
                    subject.title,
                    testTag = "subjectTitle"
                )
                Text(
                    stringResource(R.string.chapters_lessons, chapters.size, chapters.sumOf { it.lessons.size }),
                    modifier = Modifier
                        .semantics { testTagsAsResourceId = true }
                        .testTag("subjectChaptersInfo"),
                    color = TextColor.copy(alpha = 0.7f)
                )

                SearchBarView(
                    query = searchQuery,
                    placeholder = { Text(stringResource(R.string.search_for_a_lesson_or_topic)) }
                ) { searchQuery = it }

                resumeLearningProgress?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    val chapter = chapters.find { chapter -> chapter.lessons.contains(it.lesson) }

                    chapter?.let { chapterItem ->
                        TitleText(
                            text = stringResource(R.string.resume_learning),
                            testTag = "resumeLearning"
                        )

                        ResumeLearning(
                            title = it.lesson.title,
                            subtitle = stringResource(
                                R.string.you_ve_watched_of_lessons,
                                chapterItem.lessons.indexOf(it.lesson) + 1,
                                chapterItem.lessons.size
                            )
                        ) {
                            navigator.navigate(
                                LessonPlayerScreenDestination(
                                    lessons = LessonsWrapper(chapter.lessons),
                                    index = chapter.lessons.indexOf(it.lesson)
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                TitleText(
                    text = stringResource(R.string.chapters),
                    testTag = "chapterTitle"
                )
                ChaptersView(
                    chapters = chapters.map { chapter ->
                        Chapter(
                            chapter.title,
                            chapter.lessons.filter { lesson ->
                                lesson.title.lowercase().contains(searchQuery.lowercase())
                            }
                        )
                    }
                ) { lesson ->
                    val chapter = chapters.firstOrNull { it.lessons.contains(lesson) } ?: return@ChaptersView
                    navigator.navigate(
                        LessonPlayerScreenDestination(
                            lessons = LessonsWrapper(chapter.lessons),
                            index = chapter.lessons.indexOf(lesson)
                        )
                    )
                }
            }
        }
    }

    if (uiState == UIState.LOADING) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
