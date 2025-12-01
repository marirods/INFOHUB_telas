package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.FormularioCheckout
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.viewmodel.CarrinhoViewModel
import com.example.infohub_telas.viewmodel.CarrinhoUiState
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCheckout(navController: NavController) {
    val context = LocalContext.current
    // CORRIGIDO: Usar "InfoHub_Prefs" onde o login salva os dados
    val prefs = context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)
    val token = prefs.getString("jwt_token", "") ?: ""  // CORRIGIDO: jwt_token
    val userId = prefs.getInt("user_id", 0)

    Log.d("TelaCheckout", "🔐 Auth Info - UserId: $userId, Token: ${if (token.isNotBlank()) "EXISTS" else "EMPTY"}")

    val carrinhoViewModel: CarrinhoViewModel = viewModel()
    val carrinhoState by carrinhoViewModel.carrinhoState.collectAsStateWithLifecycle()

    // Carregar carrinho ao abrir a tela
    LaunchedEffect(userId) {
        Log.d("TelaCheckout", "🛒 Carregando carrinho - UserId: $userId, Token: ${token.take(20)}...")
        if (token.isNotBlank() && userId != 0) {
            carrinhoViewModel.carregarCarrinho(token, userId)
        }
    }

    // Log quando o estado mudar
    LaunchedEffect(carrinhoState) {
        when (val state = carrinhoState) {
            is CarrinhoUiState.Success -> {
                Log.d("TelaCheckout", "✅ Carrinho carregado com sucesso!")
                Log.d("TelaCheckout", "📦 Itens: ${state.itens.size}")
                Log.d("TelaCheckout", "💰 Valor Total: ${state.valorTotal}")
                state.itens.forEachIndexed { index, item ->
                    Log.d("TelaCheckout", "  Item $index: ${item.produto?.nome} - Qtd: ${item.quantidade} - Preço: ${item.produto?.preco}")
                }
            }
            is CarrinhoUiState.Error -> {
                Log.e("TelaCheckout", "❌ Erro ao carregar carrinho: ${state.message}")
            }
            is CarrinhoUiState.Loading -> {
                Log.d("TelaCheckout", "⏳ Carregando carrinho...")
            }
        }
    }

    fun navigateToPayment() {
        navController.navigate(Routes.PAGAMENTO)
    }

    fun navigateBack() {
        navController.navigateUp()
    }

    Scaffold(
        topBar = { Header(title = "Finalizar Compra", onBackClick = { navigateBack() }) }
    ) { paddingValues ->
        when (val state = carrinhoState) {
            is CarrinhoUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CarrinhoUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    FormularioCheckout(
                        navController = navController,
                        itensCarrinho = state.itens,
                        valorTotal = state.valorTotal
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    FormularioCheckout(
                        navController = navController,
                        itensCarrinho = emptyList(),
                        valorTotal = 0.0
                    )
                }
            }
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
