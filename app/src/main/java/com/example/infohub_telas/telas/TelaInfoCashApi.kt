package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.model.SaldoInfoCash
import com.example.infohub_telas.model.TransacaoInfoCash
import com.example.infohub_telas.viewmodel.InfoCashViewModel
import com.example.infohub_telas.viewmodel.InfoCashUiState
import com.example.infohub_telas.viewmodel.HistoricoUiState
import com.example.infohub_telas.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInfoCashApi(navController: NavController) {
    val viewModel: InfoCashViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val historicoState by viewModel.historicoState.collectAsState()
    val context = LocalContext.current

    // Pega o ID do usuário das preferências (ou use um ID padrão)
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", 1) // ID padrão = 1 se não encontrar

    // Carrega os dados quando a tela é exibida
    LaunchedEffect(userId) {
        viewModel.carregarPerfilCompleto(userId)
        viewModel.carregarHistoricoInfoCash(userId, 5) // Últimas 5 transações
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar com gradiente
            TopBarInfoCash(
                onBackClick = { navController.popBackStack() },
                onRefreshClick = { viewModel.recarregarTodosDados(userId) }
            )

            // Content baseado no estado da UI
            when (val state = uiState) {
                is InfoCashUiState.Loading -> {
                    LoadingContent()
                }

                is InfoCashUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.recarregarTodosDados(userId) }
                    )
                }

                is InfoCashUiState.Success -> {
                    SuccessContent(
                        saldoInfoCash = state.saldoInfoCash,
                        historicoState = historicoState
                    )
                }
            }
        }

        // Menu inferior
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            BottomMenu(navController = navController)
        }

        // Botão de chat flutuante
        FloatingActionButton(
            onClick = { /* TODO: Implementar chat */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp),
            containerColor = PrimaryOrange
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Chat,
                contentDescription = "Chat",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun TopBarInfoCash(
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(PrimaryOrange, SecondaryOrange)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Logo do InfoCash
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "💰", fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "InfoCash",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botão de refresh
            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Atualizar",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = PrimaryOrange)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Carregando seus InfoCash...",
                color = OnSurfaceGray
            )
        }
    }
}

@Composable
private fun ColumnScope.ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ops! Algo deu errado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                color = OnSurfaceGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Tentar novamente")
            }
        }
    }
}

@Composable
private fun ColumnScope.SuccessContent(
    saldoInfoCash: SaldoInfoCash,
    historicoState: HistoricoUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Card InfoCash Status com dados da API
            InfoCashStatusCardApi(saldoInfoCash)
        }

        item {
            // Card Histórico Recente
            HistoricoRecenteCard(historicoState)
        }

        item {
            // Card Como Ganhar HubCoins
            ComoGanharHubCoinsCard()
        }

        // Espaçamento para o menu inferior
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun InfoCashStatusCardApi(saldoInfoCash: SaldoInfoCash) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "InfoCash",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange
            )

            Spacer(modifier = Modifier.height(16.dp))

            // HubCoin Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BackgroundGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryOrange
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "💰", fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "HubCoin",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = saldoInfoCash.getNivelTexto(),
                        fontSize = 12.sp,
                        color = OnSurfaceGray
                    )
                }

                // Saldo do HubCoin vindo da API
                Text(
                    text = saldoInfoCash.getSaldoComVirgula(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryOrange
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progresso
            LinearProgressIndicator(
                progress = { saldoInfoCash.getProgressoAtual() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryOrange,
                trackColor = BackgroundGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mensagem do próximo nível
            Text(
                text = saldoInfoCash.getMensagemProximoNivel(),
                fontSize = 12.sp,
                color = OnSurfaceGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Informação da última atualização
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Última atualização",
                    tint = OnSurfaceGray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Atualizado recentemente",
                    fontSize = 10.sp,
                    color = OnSurfaceGray
                )
            }
        }
    }
}

@Composable
private fun HistoricoRecenteCard(historicoState: HistoricoUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Histórico Recente",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (historicoState) {
                is HistoricoUiState.Loading -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = PrimaryOrange
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Carregando histórico...")
                    }
                }

                is HistoricoUiState.Error -> {
                    Text(
                        text = "Erro ao carregar histórico",
                        color = OnSurfaceGray,
                        fontSize = 14.sp
                    )
                }

                is HistoricoUiState.Success -> {
                    if (historicoState.transacoes.isEmpty()) {
                        Text(
                            text = "Nenhuma transação ainda",
                            color = OnSurfaceGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        historicoState.transacoes.forEach { transacao ->
                            TransacaoInfoCashItem(transacao)
                            if (transacao != historicoState.transacoes.last()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = BackgroundGray, thickness = 1.dp)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransacaoInfoCashItem(transacao: TransacaoInfoCash) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transacao.descricao,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = formatTipoAcao(transacao.tipoAcao),
                fontSize = 12.sp,
                color = OnSurfaceGray
            )
            Text(
                text = formatDataTransacao(transacao.dataTransacao),
                fontSize = 11.sp,
                color = OnSurfaceGray
            )
        }

        Text(
            text = "+${transacao.pontos} HC",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryOrange
        )
    }
}

private fun formatTipoAcao(tipoAcao: String): String {
    return when (tipoAcao) {
        "avaliacao_promocao" -> "Avaliação de Promoção"
        "avaliacao_empresa" -> "Avaliação de Empresa"
        "cadastro_produto" -> "Cadastro de Produto"
        "manual" -> "Concessão Manual"
        else -> tipoAcao.replace("_", " ").replaceFirstChar { it.uppercase() }
    }
}

private fun formatDataTransacao(dataTransacao: String): String {
    // Simplificado - pode ser melhorado com uma biblioteca de data
    return try {
        val data = dataTransacao.substring(0, 10) // YYYY-MM-DD
        val partes = data.split("-")
        "${partes[2]}/${partes[1]}/${partes[0]}"
    } catch (_: Exception) {
        "Data inválida"
    }
}
