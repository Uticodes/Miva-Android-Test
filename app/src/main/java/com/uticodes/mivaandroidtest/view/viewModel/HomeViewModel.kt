package com.uticodes.mivaandroidtest.view.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.models.Subject
import com.uticodes.mivaandroidtest.usecases.GetChapterUseCase
import com.uticodes.mivaandroidtest.usecases.GetSubjectsUseCase
import com.uticodes.mivaandroidtest.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChaptersUseCase: GetChapterUseCase,
    private val getSubjectsUseCase: GetSubjectsUseCase,
) : ViewModel() {
    val uiState = mutableStateOf(UIState.IDLE)
    val chapters = mutableStateListOf<Chapter>()
    val subjects = mutableStateListOf<Subject>()
    val searchQuery = mutableStateOf("")

    fun loadSubjects() {
        viewModelScope.launch {
            subjects.clear()
            subjects.addAll(getSubjectsUseCase.getSubjects())
        }
    }

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

            uiState.value = UIState.IDLE
        }
    }
}
