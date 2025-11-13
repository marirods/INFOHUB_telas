package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.model.SaldoInfoCash
import com.example.infohub_telas.viewmodel.InfoCashViewModel
import com.example.infohub_telas.viewmodel.InfoCashUiState
import com.example.infohub_telas.ui.theme.*
import com.example.infohub_telas.navigation.Routes
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInfoCash(navController: NavController?) {
    // ViewModel e estado
    val viewModel: InfoCashViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Pega o ID do usuário e token das preferências
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", 0)
    val token = prefs.getString("token", "") ?: ""
    val isAdmin = prefs.getBoolean("isAdmin", false)

    // Log para debug
    LaunchedEffect(Unit) {
        Log.d("TelaInfoCash", "🔑 Token disponível: ${if (token.isNotEmpty()) "Sim (${token.take(20)}...)" else "NÃO - USUÁRIO NÃO LOGADO"}")
        Log.d("TelaInfoCash", "👤 User ID: $userId")

        if (token.isEmpty()) {
            Log.e("TelaInfoCash", "❌ ATENÇÃO: Token vazio! Usuário precisa fazer login.")
        }
        if (userId == 0) {
            Log.e("TelaInfoCash", "❌ ATENÇÃO: User ID inválido! Usuário precisa fazer login.")
        }
    }

    // Carrega os dados quando a tela é exibida
    LaunchedEffect(userId, token) {
        if (userId > 0 && token.isNotEmpty()) {
            Log.d("TelaInfoCash", "✅ Carregando saldo InfoCash para usuário $userId")
            viewModel.carregarSaldoInfoCash(token, userId)
        } else {
            Log.w("TelaInfoCash", "⚠️ Não é possível carregar dados: userId=$userId, token=${if (token.isEmpty()) "vazio" else "presente"}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        if (userId == 0 || token.isEmpty()) {
            // Usuário não logado: exibe mensagem e botão para login
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (userId == 0) "Faça login para ver seu InfoCash"
                           else "Sessão expirada. Faça login novamente.",
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
                            color = Color.White
                        )
                    }
                }

                // Content baseado no estado da UI
                when (uiState) {
                    is InfoCashUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = PrimaryOrange)
                        }
                    }

                    is InfoCashUiState.Error -> {
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
                                    text = (uiState as InfoCashUiState.Error).message,
                                    fontSize = 14.sp,
                                    color = OnSurfaceGray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.carregarSaldoInfoCash(token, userId) },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                                ) {
                                    Text("Tentar novamente")
                                }
                            }
                        }
                    }

                    is InfoCashUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                // Card InfoCash Status com dados da API
                                InfoCashStatusCardApi((uiState as InfoCashUiState.Success).saldoInfoCash)
                            }

                            item {
                                // Card Conquistas
                                ConquistasCard()
                            }

                            item {
                                // Card Como Ganhar HubCoins
                                ComoGanharHubCoinsCard()
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

            // Menu inferior
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
            ) {
                BottomMenu(navController = navController!!, isAdmin = isAdmin)
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
fun ComoGanharHubCoinsCard() {
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
                onClick = { /* TODO: Implementar ação */ },
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

@Preview(showSystemUi = true)
@Composable
fun TelaInfoCashPreview() {
    InfoHub_telasTheme {
        TelaInfoCash(null)
    }
}
