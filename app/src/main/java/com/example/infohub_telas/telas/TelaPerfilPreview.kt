package com.example.infohub_telas.telas

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPerfilPreview() {
    MaterialTheme {
        TelaPerfil(navController = null)
    }
}