package com.yannk.respira.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.yannk.respira.R

@Composable
fun FundoImg(){
    Image(
        painter = painterResource(id = R.drawable.fundo_tela),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}