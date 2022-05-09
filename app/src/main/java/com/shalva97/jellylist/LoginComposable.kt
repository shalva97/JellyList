package com.shalva97.jellylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Preview
@Composable
fun Login() {
    // show logo
    // server discovery
    // login input fields

    Column(modifier = Modifier.fillMaxSize()) {
        var textState by remember {
            mutableStateOf(TextFieldValue("Hello World"))
        }
        Text(text = "Some nice logo here")
        BasicTextField(value = textState, onValueChange = {
            textState = it
        })

    }
}