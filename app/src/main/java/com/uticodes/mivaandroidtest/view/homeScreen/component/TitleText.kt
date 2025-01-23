package com.uticodes.mivaandroidtest.view.homeScreen.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.uticodes.mivaandroidtest.ui.theme.TextColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = TextColor,
    fontSize: TextUnit = 30.sp,
    fontWeight: FontWeight = FontWeight.W600,
    testTag: String? = null // Optional test tag for testing
) {
    Text(
        text = text,
        modifier = modifier
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
            .semantics { testTagsAsResourceId = true },
        style = MaterialTheme.typography.displaySmall.copy(
            fontSize = fontSize,
            color = color,
        ),
        fontWeight = fontWeight
    )
}
