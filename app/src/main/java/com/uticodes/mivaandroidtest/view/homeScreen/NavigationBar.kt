package com.uticodes.mivaandroidtest.view.homeScreen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.sp
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.ui.theme.Grey
import com.uticodes.mivaandroidtest.utils.showToast

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomNavBar() {
    val context = LocalContext.current
    val navItemColor = NavigationBarItemDefaults.colors(
        unselectedIconColor = Grey,
        unselectedTextColor = Grey,
    )
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(painterResource(id = R.drawable.ic_home), "Home Icon") },
            label = { Text(stringResource(R.string.home), fontSize = 10.sp) },
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("HomeIcon")
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.showToast(context.getString(R.string.not_available))
            },
            icon = { Icon(painterResource(id = R.drawable.ic_classes), "Classes Icon") },
            label = { Text(stringResource(R.string.classes), fontSize = 10.sp) },
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("ClassesIcon"),
            colors = navItemColor
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.showToast(context.getString(R.string.not_available))
            },
            icon = { Icon(painterResource(id = R.drawable.ic_subscribe), "Subscribe Icon") },
            label = { Text(stringResource(R.string.subscribe), fontSize = 10.sp) },
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("SubscribeIcon"),
            colors = navItemColor
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.showToast(context.getString(R.string.not_available))
            },
            icon = { Icon(painterResource(id = R.drawable.ic_downloads), "Downloads Icon") },
            label = { Text(stringResource(R.string.downloads), fontSize = 10.sp) },
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("DownloadsIcon"),
            colors = navItemColor
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.showToast(context.getString(R.string.not_available))
            },
            icon = { Icon(painterResource(id = R.drawable.ic_more), "More Icon") },
            label = { Text(stringResource(R.string.more), fontSize = 10.sp) },
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("MoreIcon"),
            colors = navItemColor

        )
    }
}
