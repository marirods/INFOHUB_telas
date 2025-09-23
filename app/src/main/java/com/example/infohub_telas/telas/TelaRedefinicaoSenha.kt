package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaRedefinicaoSenha() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .background(Color(0xFFFFD966))
            ) {

            }


        }
    }
}






@Preview(showSystemUi = true)
@Composable
fun TelaRedefinicaoSenhaPreview() {
    InfoHub_telasTheme {
        TelaRedefinicaoSenha()
    }
}

