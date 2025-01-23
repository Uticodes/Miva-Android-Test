package com.uticodes.mivaandroidtest.view.homeScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.ui.theme.TextColor
import com.uticodes.mivaandroidtest.view.destinations.SubjectDetailsScreenDestination
import com.uticodes.mivaandroidtest.view.homeScreen.component.CarouselView
import com.uticodes.mivaandroidtest.view.homeScreen.component.SearchBarView
import com.uticodes.mivaandroidtest.view.viewModel.HomeViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var searchQuery by remember { viewModel.searchQuery }
    val subjects = remember { viewModel.subjects }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSubjects()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CarouselView()
            SearchBarView(
                query = searchQuery,
                placeholder = {
                    Text(
                        stringResource(R.string.what_would_you_like_to_learn),
                        color = TextColor,
                    )
                },
            ) { searchQuery = it }

            Subjects(
                modifier = Modifier.weight(1f),
                subjects.filter { it.title.lowercase().contains(searchQuery.trim().lowercase()) }
            ) { subject ->
                if (subject.title != context.getString(R.string.biology)) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.only_biology_is_available),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Subjects
                }
                navigator.navigate(SubjectDetailsScreenDestination(subject))
            }
        }
    }
}
