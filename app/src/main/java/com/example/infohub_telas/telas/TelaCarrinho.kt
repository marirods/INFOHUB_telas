package com.example.infohub_telas.telas

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.components.CarrinhoCheio
import com.example.infohub_telas.components.CarrinhoVazio
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.viewmodel.CarrinhoUiState
import com.example.infohub_telas.viewmodel.CarrinhoViewModel
import com.example.infohub_telas.viewmodel.OperationUiState

private const val TAG = "TelaCarrinho"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCarrinho(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val token = prefs.getString("token", "") ?: ""
    val userId = prefs.getInt("user_id", 0)

    // ViewModel para gerenciar o carrinho
    val viewModel: CarrinhoViewModel = viewModel()
    val carrinhoState by viewModel.carrinhoState.collectAsState()
    val operationState by viewModel.operationState.collectAsState()

    // Carregar carrinho ao abrir a tela
    LaunchedEffect(userId) {
        Log.d(TAG, "🚀 Iniciando TelaCarrinho - UserId: $userId")

        if (token.isBlank()) {
            Log.e(TAG, "❌ Token de autenticação não encontrado")
            Toast.makeText(context, "Faça login para ver seu carrinho", Toast.LENGTH_LONG).show()
        } else if (userId == 0) {
            Log.e(TAG, "❌ ID do usuário inválido")
            Toast.makeText(context, "Erro ao identificar usuário", Toast.LENGTH_LONG).show()
        } else {
            Log.d(TAG, "✅ Carregando carrinho do usuário $userId")
            viewModel.carregarCarrinho(token, userId)
        }
    }

    // Observar estado das operações e mostrar feedback
    LaunchedEffect(operationState) {
        when (val state = operationState) {
            is OperationUiState.Success -> {
                Log.d(TAG, "✅ Operação bem-sucedida: ${state.message}")
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetOperationState()
            }
            is OperationUiState.Error -> {
                Log.e(TAG, "❌ Erro na operação: ${state.message}")
                Toast.makeText(context, "Erro: ${state.message}", Toast.LENGTH_LONG).show()
                viewModel.resetOperationState()
            }
            else -> {
                // Idle ou Loading - não fazer nada
            }
        }
    }

    fun navigateToCheckout() {
        Log.d(TAG, "🛒 Navegando para Checkout")
        navController.navigate(Routes.CHECKOUT)
    }

    fun navigateToHome() {
        Log.d(TAG, "🏠 Navegando para Home")
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.HOME) { inclusive = true }
        }
    }

    fun navigateBack() {
        Log.d(TAG, "⬅️ Voltando para tela anterior")
        navController.navigateUp()
    }

    Scaffold(
        topBar = { Header(title = "Meu Carrinho", onBackClick = { navigateBack() }) },
        bottomBar = { BottomMenu(navController = navController, isAdmin = isAdmin) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Renderizar baseado no estado da API
            when (val state = carrinhoState) {
                is CarrinhoUiState.Loading -> {
                    // Estado de carregamento
                    Log.d(TAG, "⏳ Estado: Loading - Carregando carrinho...")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CarrinhoUiState.Success -> {
                    val itens = state.itens
                    val valorTotal = state.valorTotal

                    Log.d(TAG, "✅ Estado: Success - ${itens.size} itens, Total: R$ $valorTotal")

                    if (itens.isEmpty()) {
                        // Carrinho vazio
                        Log.d(TAG, "📦 Carrinho vazio - Mostrando CarrinhoVazio")
                        CarrinhoVazio(navController = navController)
                    } else {
                        // Carrinho com itens
                        Log.d(TAG, "🛒 Carrinho com ${itens.size} itens - Mostrando CarrinhoCheio")
                        CarrinhoCheio(navController = navController)
                    }
                }

                is CarrinhoUiState.Error -> {
                    // Estado de erro - mostrar carrinho vazio como fallback
                    Log.e(TAG, "❌ Estado: Error - ${state.message}")
                    Log.e(TAG, "⚠️ Mostrando CarrinhoVazio como fallback devido ao erro")

                    // Mostrar toast com erro
                    LaunchedEffect(state.message) {
                        Toast.makeText(
                            context,
                            "Erro ao carregar carrinho: ${state.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    CarrinhoVazio(navController = navController)
                }
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
