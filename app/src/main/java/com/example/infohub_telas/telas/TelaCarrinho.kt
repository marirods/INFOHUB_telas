package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import com.example.infohub_telas.components.CarrinhoVazio
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.viewmodel.CarrinhoViewModel
import com.example.infohub_telas.viewmodel.CarrinhoUiState
import com.example.infohub_telas.viewmodel.OperationUiState
import com.example.infohub_telas.utils.AppUtils

private const val TAG = "TelaCarrinho"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCarrinho(navController: NavController) {
    val context = LocalContext.current
    // CORRIGIDO: Usar InfoHub_Prefs onde o login salva os dados
    val prefs = context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)

    // Estado para controlar rolagem e visibilidade do menu
    val lazyListState = rememberLazyListState()
    val isMenuVisible = lazyListState.rememberMenuVisibility()
    val token = prefs.getString("jwt_token", "") ?: ""  // CORRIGIDO: jwt_token
    val userId = prefs.getInt("user_id", 0)

    // Log para verificar se o token está disponível
    LaunchedEffect(Unit) {
        Log.d("TelaCarrinho", "🔑 Token disponível: ${if (token.isNotEmpty()) "Sim (${token.take(20)}...)" else "NÃO - USUÁRIO NÃO LOGADO"}")
        Log.d("TelaCarrinho", "👤 User ID: $userId")
        if (token.isEmpty()) {
            Log.e("TelaCarrinho", "❌ ATENÇÃO: Token vazio! Usuário precisa fazer login.")
        }
        if (userId == 0) {
            Log.e("TelaCarrinho", "❌ ATENÇÃO: User ID inválido! Usuário precisa fazer login.")
        }
    }

    // ViewModel para gerenciar o carrinho
    val carrinhoViewModel: CarrinhoViewModel = viewModel()
    val carrinhoState by carrinhoViewModel.carrinhoState.collectAsStateWithLifecycle()
    val operationState by carrinhoViewModel.operationState.collectAsStateWithLifecycle()

    // Carregar carrinho ao abrir a tela
    LaunchedEffect(userId) {
        Log.d(TAG, "🚀 Iniciando TelaCarrinho - UserId: $userId")

        if (token.isBlank()) {
            Log.e(TAG, "❌ Token de autenticação não encontrado")
            Toast.makeText(context, "Faça login para ver seu carrinho", Toast.LENGTH_LONG).show()
        } else if (userId == 0) {
            Log.e(TAG, "❌ ID do usuário inválido")
            // Toast removido - não mostrar erros ao usuário
        } else {
            Log.d(TAG, "✅ Carregando carrinho do usuário $userId")
            carrinhoViewModel.carregarCarrinho(token, userId)
        }
    }

    // Observar estados
    LaunchedEffect(carrinhoState) {
        when (val state = carrinhoState) {
            is CarrinhoUiState.Error -> {
                AppUtils.showErrorToast(context, state.message)
            }
            else -> { /* Handle other states if needed */ }
        }
    }

    LaunchedEffect(operationState) {
        when (val state = operationState) {
            is OperationUiState.Success -> {
                Toast.makeText(context, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show()
                // Reload cart after successful operation
                carrinhoViewModel.carregarCarrinho(token, userId)
            }
            is OperationUiState.Error -> {
                AppUtils.showErrorToast(context, state.message)
            }
            else -> { /* Handle other states */ }
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
        topBar = { Header(title = "Meu Carrinho", onBackClick = { navigateBack() }) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Renderizar baseado no estado do ViewModel
                when (carrinhoState) {
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
                        val state = carrinhoState as CarrinhoUiState.Success
                        val itens = state.itens
                        val valorTotal = state.valorTotal

                        Log.d(TAG, "✅ Estado: Success - ${itens.size} itens, Total: R$ ${AppUtils.formatarMoeda(valorTotal)}")

                        if (itens.isNotEmpty()) {
                            // Carrinho com itens
                            Log.d(TAG, "🛒 Carrinho com ${itens.size} itens - Mostrando produtos")

                            Column(modifier = Modifier.fillMaxSize()) {
                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    state = lazyListState
                                ) {
                                    items(itens) { item ->
                                        com.example.infohub_telas.components.ItemCarrinhoCard(
                                            item = item,
                                            onRemove = {
                                                carrinhoViewModel.removerItem(token, userId, item.idProduto)
                                            },
                                            onUpdateQuantidade = { novaQuantidade ->
                                                carrinhoViewModel.atualizarQuantidade(
                                                    token = token,
                                                    idUsuario = userId,
                                                    idCarrinho = item.idCarrinho,
                                                    novaQuantidade = novaQuantidade
                                                )
                                            }
                                        )
                                    }
                                }

                                // Card com total e botão de finalizar compra
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Total: R$ ${AppUtils.formatarMoeda(valorTotal)}",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { navigateToCheckout() },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF25992E)
                                            )
                                        ) {
                                            Text("Finalizar Compra")
                                        }
                                    }
                                }
                            }
                        } else {
                            // Carrinho vazio
                            Log.d(TAG, "📦 Carrinho vazio - Mostrando CarrinhoVazio")
                            CarrinhoVazio(navController = navController)
                        }
                    }

                    is CarrinhoUiState.Error -> {
                        // Estado de erro
                        val state = carrinhoState as CarrinhoUiState.Error
                        Log.e(TAG, "❌ Estado: Error - ${state.message}")
                        CarrinhoVazio(navController = navController)
                    }
                }
            }

            // Menu inferior animado
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimatedScrollableBottomMenu(
                    navController = navController,
                    isAdmin = isAdmin,
                    isVisible = isMenuVisible
                )
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
