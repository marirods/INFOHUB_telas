package com.example.infohub_telas.telas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.primaryLight
import com.example.infohub_telas.utils.AppUtils
// Modelo local para substituir ViewModel
data class MensagemChat(
    val id: Long = System.currentTimeMillis(),
    val texto: String,
    val isUsuario: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val isError: Boolean = false
)

@Composable
fun TelaChatDePrecos(
    navController: NavController? = null
) {
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }

    // Estados locais para substituir o ViewModel
    var isLoading by remember { mutableStateOf(false) }
    var mensagens by remember { mutableStateOf<List<MensagemChat>>(
        listOf(
            MensagemChat(
                texto = "Olá! Eu sou sua assistente IA para comparação de preços. Como posso ajudá-lo hoje?",
                isUsuario = false,
                timestamp = System.currentTimeMillis()
            )
        )
    )}
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    // Estado para controlar quando processar mensagens
    var textoParaProcessar by remember { mutableStateOf<String?>(null) }

    // Função para enviar mensagem (simulação)
    fun enviarMensagem(texto: String) {
        if (texto.isBlank()) return

        // Adiciona mensagem do usuário
        val mensagemUsuario = MensagemChat(
            texto = texto,
            isUsuario = true,
            timestamp = System.currentTimeMillis()
        )
        mensagens = mensagens + mensagemUsuario

        // Simula processamento da IA
        isLoading = true
        textoParaProcessar = texto
    }

    // LaunchedEffect para simular resposta da IA
    LaunchedEffect(textoParaProcessar) {
        textoParaProcessar?.let { texto ->
            kotlinx.coroutines.delay(1500)

            val respostaIA = when {
                texto.contains("comparar", ignoreCase = true) ->
                    "Para comparar preços, digite o nome do produto que você procura. Nossa IA analisa ofertas de diversos estabelecimentos parceiros e mostra as melhores opções para você!"
                texto.contains("época", ignoreCase = true) || texto.contains("quando", ignoreCase = true) ->
                    "As melhores épocas para comprar são: Black Friday (novembro), liquidações de fim de ano, início do ano (janeiro) e datas sazonais específicas de cada categoria de produto."
                texto.contains("dicas", ignoreCase = true) || texto.contains("economia", ignoreCase = true) ->
                    "Dicas de economia: 1) Compare preços antes de comprar, 2) Use nossos alertas de preço, 3) Aproveite promoções sazonais, 4) Acumule pontos no InfoCash, 5) Verifique cupons disponíveis."
                texto.contains("sistema", ignoreCase = true) || texto.contains("funciona", ignoreCase = true) ->
                    "Nosso sistema monitora preços em tempo real de estabelecimentos parceiros. Você pesquisa um produto, nossa IA encontra as melhores ofertas, compara preços e histórico, e sugere a melhor opção de compra!"
                else ->
                    "Entendo que você está procurando por '$texto'. Estou analisando as melhores ofertas disponíveis... Em breve teremos informações mais detalhadas sobre este produto!"
            }

            val mensagemIA = MensagemChat(
                texto = respostaIA,
                isUsuario = false,
                timestamp = System.currentTimeMillis()
            )
            mensagens = mensagens + mensagemIA
            isLoading = false
            // textoParaProcessar = null (removido para evitar warning)
        }
    }

    // Tratar erros
    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            AppUtils.showErrorToast(context, message)
            // errorMessage = null (removido para evitar warning)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header laranja
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(primaryLight),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(top = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController?.navigateUp() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chat de Preços IA",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Área de mensagens do chat
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                state = listState
            ) {
                // Subtítulo
                item {
                    Text(
                        text = "Compare preços instantaneamente com nossa IA",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF9A01B),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Mensagens do chat
                items(mensagens) { mensagem ->
                    ChatMessageItemPrecos(mensagem)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Botão de opções
                item {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFF9A01B), RoundedCornerShape(12.dp))
                                .clickable { showOptions = !showOptions }
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menu),
                                    contentDescription = "Menu",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (showOptions) "Fechar opções" else "Abrir opções",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = if (showOptions) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Lista de opções (aparece quando showOptions é true)
                        if (showOptions) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(Color(0xFFFFF8E7), RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "Selecione uma opção:",
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )
                                    OptionItemPrecos("🔍 Comparar preços de produtos") {
                                        if (!isLoading) {
                                            enviarMensagem("Quero comparar preços de produtos. Como posso fazer isso?")
                                            showOptions = false
                                        }
                                    }
                                    OptionItemPrecos("🛒 Melhor época para comprar") {
                                        if (!isLoading) {
                                            enviarMensagem("Qual é a melhor época para comprar produtos com desconto?")
                                            showOptions = false
                                        }
                                    }
                                    OptionItemPrecos("💡 Dicas de economia") {
                                        if (!isLoading) {
                                            enviarMensagem("Me dê dicas para economizar nas compras")
                                            showOptions = false
                                        }
                                    }
                                    OptionItemPrecos("❓ Como funciona o sistema") {
                                        if (!isLoading) {
                                            enviarMensagem("Como funciona o sistema de comparação de preços do InfoHub?")
                                            showOptions = false
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Campo de input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .background(Color(0xFFF6F6F6), RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    decorationBox = { innerTextField ->
                        if (inputText.isEmpty()) {
                            Text(
                                text = "Digite o produto que você procura...",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    }
                )
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank() && !isLoading) {
                            enviarMensagem(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = if (inputText.isNotBlank()) Color(0xFFF9A01B) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageItemPrecos(mensagem: MensagemChat) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (mensagem.isUsuario) Arrangement.End else Arrangement.Start
    ) {
        if (!mensagem.isUsuario) {
            Icon(
                painter = painterResource(id = R.drawable.robo),
                contentDescription = "Robô",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp),
                tint = Color.Unspecified
            )
        }

        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = when {
                        mensagem.isUsuario -> Color(0xFFF9A01B)
                        mensagem.isError -> Color(0xFFFFE5E5)
                        else -> Color(0xFFFFF8E7)
                    },
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (mensagem.isUsuario) 16.dp else 4.dp,
                        bottomEnd = if (mensagem.isUsuario) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                    .format(java.util.Date(mensagem.timestamp)),
                fontSize = 11.sp,
                color = when {
                    mensagem.isUsuario -> Color.White.copy(alpha = 0.8f)
                    mensagem.isError -> Color.Red.copy(alpha = 0.7f)
                    else -> Color.Gray
                },
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Texto da mensagem
            Text(
                text = mensagem.texto,
                fontSize = 14.sp,
                color = when {
                    mensagem.isUsuario -> Color.White
                    mensagem.isError -> Color.Red
                    else -> Color.Black
                },
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun OptionItemPrecos(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
