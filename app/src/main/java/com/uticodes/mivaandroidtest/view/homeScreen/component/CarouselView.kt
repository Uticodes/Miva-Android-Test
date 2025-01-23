package com.uticodes.mivaandroidtest.view.homeScreen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.ui.theme.White

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CarouselView() {
    val pagerState = rememberPagerState(pageCount = { 4 })

    val imageResources = listOf(
        R.drawable.ic_carousel,
        R.drawable.ic_carousel,
        R.drawable.ic_carousel,
        R.drawable.ic_carousel,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { testTagsAsResourceId = true }.testTag("CarouselView"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp),
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = imageResources[page]),
                    contentDescription = "Page $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().semantics { testTagsAsResourceId = true }.testTag("Carousel Image"),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 7.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    CustomPagerRectangleIndicator(
                        totalPages = imageResources.size,
                        currentPage = pagerState.currentPage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { testTagsAsResourceId = true }.testTag("Carousel Indicator"),
                    )
                }
            }
        }
    }
}

@Composable
fun CustomPagerRectangleIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalPages) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (i == currentPage) White else White.copy(alpha = 0.3f))
            )
            if (i < totalPages - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}
