package com.uticodes.mivaandroidtest.view.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uticodes.mivaandroidtest.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResumeLearning(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("resumeLearning")
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(Color(0xFFEA7052))
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_resume),
            contentDescription = "Resume Icon",
            tint = Color.Unspecified
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                title,
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 24.sp
                ),
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                    fontSize = 12.sp,
                ),
            )
        }
    }
}
