package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.FormularioCheckout
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCheckout(navController: NavController) {
    Scaffold(
        topBar = { Header(title = "Finalizar Compra") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            FormularioCheckout(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaCheckoutPreview() {
    InfoHub_telasTheme {
        TelaCheckout(rememberNavController())
    }
}
