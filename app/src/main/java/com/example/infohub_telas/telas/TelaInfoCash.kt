package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.network.models.SaldoInfoCash
import com.example.infohub_telas.network.models.TransacaoInfoCash
import com.example.infohub_telas.ui.theme.*
import com.example.infohub_telas.utils.AppUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInfoCash(
    navController: NavController?
) {
    val context = LocalContext.current

    // Pega o ID do usuário das preferências
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", 0)
    val isAdmin = prefs.getBoolean("isAdmin", false)

    // Estados locais para substituir o ViewModel
    var saldoInfoCash by remember { mutableStateOf<SaldoInfoCash?>(null) }
    var historicoTransacoes by remember { mutableStateOf<List<TransacaoInfoCash>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estado para controlar rolagem e visibilidade do menu
    val lazyListState = rememberLazyListState()
    val isMenuVisible = true

    // Função para recarregar dados (simulação)
    fun recarregarDados() {
        if (userId > 0) {
            Log.d("TelaInfoCash", "🔄 Recarregando dados do InfoCash")
            isLoading = true
            errorMessage = null
        }
    }

    // Carrega os dados quando a tela é exibida
    LaunchedEffect(userId) {
        if (userId > 0) {
            Log.d("TelaInfoCash", "✅ Carregando perfil InfoCash para usuário $userId")
            isLoading = true

            // Simular dados para evitar crash
            kotlinx.coroutines.delay(500)
            saldoInfoCash = SaldoInfoCash(
                idUsuario = userId,
                saldoTotal = 1250,
                ultimaAtualizacao = "2024-01-20T10:30:00"
            )
            historicoTransacoes = listOf(
                TransacaoInfoCash(
                    idTransacao = 1,
                    idUsuario = userId,
                    tipoAcao = "compra",
                    pontos = 100,
                    descricao = "Compra na loja",
                    dataTransacao = "2024-01-20T10:30:00",
                    referenciaId = null
                )
            )
            isLoading = false
        } else {
            Log.w("TelaInfoCash", "⚠️ User ID inválido: $userId")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        if (userId == 0) {
            // Usuário não logado: exibe mensagem e botão para login
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Faça login para ver seu InfoCash",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController?.navigate(Routes.LOGIN) }) {
                    Text("Ir para Login")
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Bar com gradiente
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
                        IconButton(
                            onClick = { navController?.popBackStack() }
                        ) {
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
                                Text(
                                    text = "💰",
                                    fontSize = 24.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "InfoCash",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        // Botão de refresh
                        IconButton(
                            onClick = { recarregarDados() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Recarregar",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Content baseado nos estados locais
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = PrimaryOrange)
                        }
                    }

                    errorMessage != null -> {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = errorMessage ?: "Erro ao carregar dados do InfoCash",
                                    fontSize = 14.sp,
                                    color = OnSurfaceGray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { recarregarDados() },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                                ) {
                                    Text("Tentar novamente")
                                }
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                // Card InfoCash Status com dados locais
                                saldoInfoCash?.let { saldo ->
                                    InfoCashStatusCardApi(saldo)
                                } ?: run {
                                    // Card de loading ou placeholder
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(120.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("Carregando saldo...")
                                        }
                                    }
                                }
                            }

                            item {
                                // Card Histórico Recente
                                HistoricoRecenteCardNovo(historicoTransacoes)
                            }

                            item {
                                // Card Conquistas
                                ConquistasCard()
                            }

                            item {
                                // Card Como Ganhar HubCoins
                                ComoGanharHubCoinsCard(navController)
                            }

                            item {
                                // Card Comunidade
                                ComunidadeCard()
                            }

                            // Espaçamento para o menu inferior
                            item {
                                Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }
                }
            }

            // FloatingActionButton para Chat
            FloatingActionButton(
                onClick = { navController?.navigate(Routes.CHAT_PRECOS) },
                containerColor = PrimaryOrange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 96.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Chat IA",
                    tint = Color.White
                )
            }

            // Menu inferior animado
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                AnimatedScrollableBottomMenu(
                    navController = navController,
                    isAdmin = isAdmin,
                    isVisible = isMenuVisible
                )
            }
        }
    }
}

@Composable
fun InfoCashStatusCardApi(saldoInfoCash: SaldoInfoCash) {
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
                        Text(
                            text = "💰",
                            fontSize = 20.sp
                        )
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
                        text = getNivelTexto(saldoInfoCash.saldoTotal),
                        fontSize = 12.sp,
                        color = OnSurfaceGray
                    )
                }

                // Saldo do HubCoin vindo da API
                Text(
                    text = "${saldoInfoCash.saldoTotal}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryOrange
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progresso
            LinearProgressIndicator(
                progress = { getProgressoAtual(saldoInfoCash.saldoTotal) },
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
                text = getMensagemProximoNivel(saldoInfoCash.saldoTotal),
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
                    text = "Atualizado em ${saldoInfoCash.ultimaAtualizacao?.let { AppUtils.formatarData(it) } ?: "agora"}",
                    fontSize = 10.sp,
                    color = OnSurfaceGray
                )
            }
        }
    }
}

// Funções auxiliares para o InfoCash
private fun getNivelTexto(saldo: Int): String {
    return when {
        saldo < 100 -> "Iniciante"
        saldo < 500 -> "Bronze"
        saldo < 1000 -> "Prata"
        saldo < 2000 -> "Ouro"
        else -> "Platina"
    }
}

private fun getProgressoAtual(saldo: Int): Float {
    return when {
        saldo < 100 -> saldo / 100f
        saldo < 500 -> (saldo - 100) / 400f
        saldo < 1000 -> (saldo - 500) / 500f
        saldo < 2000 -> (saldo - 1000) / 1000f
        else -> 1f
    }
}

private fun getMensagemProximoNivel(saldo: Int): String {
    return when {
        saldo < 100 -> "Faltam ${100 - saldo} pontos para Bronze"
        saldo < 500 -> "Faltam ${500 - saldo} pontos para Prata"
        saldo < 1000 -> "Faltam ${1000 - saldo} pontos para Ouro"
        saldo < 2000 -> "Faltam ${2000 - saldo} pontos para Platina"
        else -> "Nível máximo alcançado!"
    }
}

@Composable
fun ConquistasCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Conquistas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid de conquistas 2x2
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ConquistaItem(
                        title = "Comprador de Ofertas",
                        subtitle = "Finalize sua primeira compra",
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                    ConquistaItem(
                        title = "Negociador",
                        subtitle = "Negocie um preço com sucesso",
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ConquistaItem(
                        title = "Insider Expert",
                        subtitle = "Compartilhe 10 dicas valiosas",
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                    ConquistaItem(
                        title = "Economiasta Pro",
                        subtitle = "Economize R$100 em compras",
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ConquistaItem(
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = if (isCompleted) PrimaryGreen.copy(alpha = 0.1f) else BackgroundGray
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isCompleted) {
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = PrimaryGreen
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = OnSurfaceGray
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "○",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = if (isCompleted) "CONQUISTADO" else "EM PROGRESSO",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCompleted) PrimaryGreen else OnSurfaceGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = OnSurfaceGray,
                lineHeight = 12.sp
            )
        }
    }
}

@Composable
fun ComoGanharHubCoinsCard(navController: NavController?) {
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
                text = "Como ganhar HubCoins",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ícones ilustrativos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = PrimaryOrange.copy(alpha = 0.1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🛍️", fontSize = 24.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Fazer\ncompras",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = OnSurfaceGray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = PrimaryOrange.copy(alpha = 0.1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "💬", fontSize = 24.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Participar\ndiscussões",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = OnSurfaceGray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = PrimaryOrange.copy(alpha = 0.1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⭐", fontSize = 24.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Avaliar\nprodutos",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = OnSurfaceGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController?.navigate(Routes.GANHAR_HUBCOINS)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrange
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Começar a ganhar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ComunidadeCard() {
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
                text = "COMUNIDADE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Card com gradiente para destaque
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = PrimaryOrange
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🏆",
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ótimo atendimento e produtos de qualidade",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Estrelas
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// HistoricoRecenteCard removed due to missing HistoricoUiState import

@Composable
fun HistoricoRecenteCardNovo(transacoes: List<TransacaoInfoCash>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Histórico Recente",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceGray
                )

                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Histórico",
                    tint = PrimaryOrange,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (transacoes.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📭",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nenhuma transação ainda",
                        fontSize = 14.sp,
                        color = OnSurfaceGray.copy(alpha = 0.6f)
                    )
                }
            } else {
                transacoes.take(5).forEach { transacao ->
                    TransacaoItemNovo(transacao)
                    if (transacao != transacoes.last()) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun TransacaoItemNovo(transacao: TransacaoInfoCash) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackgroundGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone baseado no tipo
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(20.dp),
            color = when (transacao.tipoAcao) {
                "avaliacao_promocao" -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                "avaliacao_empresa" -> Color(0xFFFFA726).copy(alpha = 0.1f)
                "cadastro_produto" -> Color(0xFF42A5F5).copy(alpha = 0.1f)
                else -> PrimaryOrange.copy(alpha = 0.1f)
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (transacao.tipoAcao) {
                        "avaliacao_promocao" -> "⭐"
                        "avaliacao_empresa" -> "🏢"
                        "cadastro_produto" -> "📦"
                        else -> "💰"
                    },
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transacao.descricao,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurfaceGray
            )

            Text(
                text = AppUtils.formatarDataSimples(transacao.dataTransacao),
                fontSize = 12.sp,
                color = OnSurfaceGray.copy(alpha = 0.6f)
            )
        }

        Text(
            text = "+${transacao.pontos} HC",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = when (transacao.tipoAcao) {
                "avaliacao_promocao" -> Color(0xFF4CAF50)
                "avaliacao_empresa" -> Color(0xFFFFA726)
                "cadastro_produto" -> Color(0xFF42A5F5)
                else -> PrimaryOrange
            }
        )
    }
}



// Preview removido - pode ser adicionado quando necessário
// @Preview(showSystemUi = true)
// @Composable
// fun TelaInfoCashPreview() {
//     InfoHub_telasTheme {
//         TelaInfoCash(null)
//     }
// }
