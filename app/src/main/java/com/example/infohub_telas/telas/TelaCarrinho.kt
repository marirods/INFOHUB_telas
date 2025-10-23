package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.CarrinhoCheio
import com.example.infohub_telas.components.CarrinhoVazio
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCarrinho(navController: NavController) {
    // Estado para simular se o carrinho está vazio ou não
    var carrinhoVazio by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Header(title = "Meu Carrinho") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (carrinhoVazio) {
                CarrinhoVazio(navController = navController)
            } else {
                CarrinhoCheio(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaCarrinhoPreview() {
    InfoHub_telasTheme {
        TelaCarrinho(rememberNavController())
    }
}
