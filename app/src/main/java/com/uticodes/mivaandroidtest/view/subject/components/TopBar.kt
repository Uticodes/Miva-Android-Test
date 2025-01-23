package com.uticodes.mivaandroidtest.view.subject.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigator: DestinationsNavigator) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {},
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(horizontal = 10.dp).clickable { navigator.popBackStack() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
            )
        },
    )
}
