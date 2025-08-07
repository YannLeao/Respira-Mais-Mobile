package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yannk.respira.ui.theme.ButtonColor

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp),
            color = ButtonColor,
            strokeWidth = 5.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
private fun PopUpLoading() {
    LoadingDialog()
}
