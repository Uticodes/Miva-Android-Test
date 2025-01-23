package com.uticodes.mivaandroidtest.view.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.models.LearningProgress
import com.uticodes.mivaandroidtest.data.models.Subject
import com.uticodes.mivaandroidtest.usecases.GetChapterUseCase
import com.uticodes.mivaandroidtest.usecases.ResumeLearningUseCase
import com.uticodes.mivaandroidtest.utils.UIState
import com.uticodes.mivaandroidtest.view.destinations.SubjectDetailsScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val getChaptersUseCase: GetChapterUseCase,
    private val resumeLearningUseCase: ResumeLearningUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs = SubjectDetailsScreenDestination.argsFrom(savedStateHandle)
    val subject: Subject
        get() = navArgs.subject

    val resumeLearningProgress = mutableStateOf<LearningProgress?>(null)

    val uiState = mutableStateOf(UIState.IDLE)
    val chapters = mutableStateListOf<Chapter>()
    val searchQuery = mutableStateOf("")

    fun loadChapters() {
        uiState.value = UIState.LOADING
        viewModelScope.launch {
            getChaptersUseCase.getChapters().collect { result ->

                result.onSuccess {
                    chapters.clear()
                    chapters.addAll(it)
                }.onFailure {
                    uiState.value = UIState.ERROR
                }
            }

            resumeLearningProgress.value = resumeLearningUseCase.getSavedProgress()

            uiState.value = UIState.IDLE
        }
    }
}
