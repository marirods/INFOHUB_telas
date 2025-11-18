package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import com.example.infohub_telas.model.Avaliacao
import com.example.infohub_telas.model.CriarAvaliacaoRequest
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.primaryLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaAvaliarEstabelecimentos(navController: NavController?) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val token = prefs.getString("token", "") ?: ""
    val userId = prefs.getInt("userId", 0)

    var avaliacoes by remember { mutableStateOf(listOf(
        // Avaliação mokada para demonstração
        Avaliacao(
            idAvaliacao = 1,
            idUsuario = 1,
            idProduto = null,
            nota = 5,
            comentario = "Estabelecimento excelente! Produtos frescos e preços justos. Sempre encontro o que preciso e o atendimento é ótimo. Super recomendo! 🛒✨",
            dataAvaliacao = "2025-11-18T10:30:00Z",
            nomeUsuario = "Maria Silva",
            curtidas = 24,
            curtidoPorUsuario = false
        )
    )) }

    var novoComentario by remember { mutableStateOf("") }
    var notaSelecionada by remember { mutableStateOf(5) }
    var isPosting by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isMenuVisible = listState.rememberMenuVisibility()

    val retrofitFactory = remember { RetrofitFactory() }
    val avaliacaoApiService = remember { retrofitFactory.getAvaliacaoApiService() }

    suspend fun postarAvaliacao(comentario: String, nota: Int) {
        if (token.isBlank()) {
            Log.e("TelaAvaliar", "❌ Token não encontrado")
            return
        }

        try {
            Log.d("TelaAvaliar", "🚀 Postando avaliação...")

            val request = CriarAvaliacaoRequest(
                idUsuario = userId,
                idProduto = null,
                nota = nota,
                comentario = comentario
            )

            val response = withContext(Dispatchers.IO) {
                avaliacaoApiService.criarAvaliacao("Bearer $token", request).execute()
            }

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("TelaAvaliar", "✅ Avaliação criada: $body")

                // Adicionar nova avaliação à lista
                body?.avaliacao?.let { novaAvaliacao ->
                    avaliacoes = listOf(novaAvaliacao) + avaliacoes
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Avaliação publicada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("TelaAvaliar", "❌ Erro: ${response.code()} - ${response.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao publicar avaliação", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("TelaAvaliar", "💥 Exceção: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun curtirAvaliacao(avaliacaoId: Int) {
        avaliacoes = avaliacoes.map { avaliacao ->
            if (avaliacao.idAvaliacao == avaliacaoId) {
                avaliacao.copy(
                    curtidas = if (avaliacao.curtidoPorUsuario) avaliacao.curtidas - 1 else avaliacao.curtidas + 1,
                    curtidoPorUsuario = !avaliacao.curtidoPorUsuario
                )
            } else {
                avaliacao
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryLight)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { navController?.navigateUp() }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Avaliar Estabelecimentos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Conteúdo com scroll
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // Card para criar nova avaliação
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Avatar do usuário
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(primaryLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Avatar",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Compartilhe sua experiência",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Seletor de estrelas
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Estrela ${index + 1}",
                                        tint = if (index < notaSelecionada) Color(0xFFFFC107) else Color.LightGray,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable { notaSelecionada = index + 1 }
                                            .padding(4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Campo de texto
                            OutlinedTextField(
                                value = novoComentario,
                                onValueChange = { novoComentario = it },
                                placeholder = { Text("Escreva seu comentário...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryLight,
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                maxLines = 5
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Botão publicar
                            Button(
                                onClick = {
                                    if (novoComentario.isNotBlank()) {
                                        isPosting = true
                                        coroutineScope.launch {
                                            postarAvaliacao(novoComentario, notaSelecionada)
                                            novoComentario = ""
                                            notaSelecionada = 5
                                            isPosting = false
                                        }
                                    } else {
                                        Toast.makeText(context, "Escreva um comentário", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primaryLight
                                ),
                                enabled = !isPosting
                            ) {
                                if (isPosting) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = "Publicar Avaliação",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // Título da seção de avaliações
                item {
                    Text(
                        text = "Avaliações da Comunidade",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Lista de avaliações
                items(avaliacoes) { avaliacao ->
                    AvaliacaoCard(
                        avaliacao = avaliacao,
                        onCurtir = { curtirAvaliacao(avaliacao.idAvaliacao) }
                    )
                }
            }

            // Menu inferior
            AnimatedScrollableBottomMenu(
                navController = navController,
                isAdmin = isAdmin,
                isVisible = isMenuVisible
            )
        }
    }
}

@Composable
fun AvaliacaoCard(
    avaliacao: Avaliacao,
    onCurtir: () -> Unit
) {
    var isLiked by remember { mutableStateOf(avaliacao.curtidoPorUsuario) }
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "like_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Cabeçalho do card
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(primaryLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avaliacao.nomeUsuario?.firstOrNull()?.uppercase() ?: "U",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = avaliacao.nomeUsuario ?: "Usuário",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    // Estrelas
                    Row {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Estrela",
                                tint = if (index < avaliacao.nota) Color(0xFFFFC107) else Color.LightGray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // Tempo
                Text(
                    text = formatarTempo(avaliacao.dataAvaliacao),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Comentário
            Text(
                text = avaliacao.comentario,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ações
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Curtir
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            isLiked = !isLiked
                            onCurtir()
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = if (isLiked) Color.Red else Color.Gray,
                        modifier = Modifier
                            .size(20.dp)
                            .scale(scale)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${avaliacao.curtidas}",
                        fontSize = 14.sp,
                        color = if (isLiked) Color.Red else Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Comentar (futuro)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comentar",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Comentar",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Compartilhar (futuro)
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartilhar",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { /* TODO: Implementar compartilhar */ }
                )
            }
        }
    }
}

fun formatarTempo(dataAvaliacao: String): String {
    // TODO: Implementar parsing de data real
    // Por enquanto retorna algo fixo baseado na data fornecida
    return if (dataAvaliacao.isNotEmpty()) "Há 2 horas" else "Agora"
}

