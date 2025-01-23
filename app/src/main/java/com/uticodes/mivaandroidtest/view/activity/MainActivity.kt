package com.uticodes.mivaandroidtest.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ramcosta.composedestinations.DestinationsNavHost
import com.uticodes.mivaandroidtest.ui.theme.MivaAndroidTestTheme
import com.uticodes.mivaandroidtest.view.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MivaAndroidTestTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
