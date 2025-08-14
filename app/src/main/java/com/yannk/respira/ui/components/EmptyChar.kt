package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EmptyChart(
    modifier: Modifier,
    chartSize: Dp = 200.dp,
    isEmptySession: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(chartSize),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.InsertChart,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                tint = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEmptySession) {
                "Nenhuma sessão realizada"
            } else {
                "Nenhum evento registrado na última sessão"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}