@file:OptIn(ExperimentalMaterial3Api::class)

package com.uticodes.mivaandroidtest.view.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.ui.theme.Grey
import com.uticodes.mivaandroidtest.ui.theme.White

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(
    query: String,
    placeholder: @Composable () -> Unit,
    onQueryChange: (value: String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Transparent)
            .border(1.dp, Grey.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
            .semantics { testTagsAsResourceId = true }
            .testTag("searchBarView"),
        value = query,
        placeholder = placeholder,
        onValueChange = onQueryChange,
        trailingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search")
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = White,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}
