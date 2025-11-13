package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.model.ChatRequest
import com.example.infohub_telas.model.ChatResponse
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.primaryLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val time: String,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

@Composable
fun TelaChatDePrecos(navController: NavController?) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val token = prefs.getString("token", "") ?: ""

    var inputText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }
    var isLoadingResponse by remember { mutableStateOf(false) }
    var messages by remember { mutableStateOf(listOf(
        ChatMessage(
            text = "Olá! Sou sua assistente de compras inteligente. Posso ajudar você a encontrar os melhores preços de qualquer produto. Digite o nome do produto que você procura!",
            isUser = false,
            time = getCurrentTime()
        )
    )) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Instância do serviço da API
    val retrofitFactory = remember { RetrofitFactory() }
    val chatApiService = remember { retrofitFactory.getInfoHub_UserService() }

    // ID único de sessão do chat
    val sessionId = remember { UUID.randomUUID().toString() }

    fun showLoadingMessage() {
        val loadingMessage = ChatMessage(
            text = "Digitando...",
            isUser = false,
            time = getCurrentTime(),
            isLoading = true
        )
        messages = messages + loadingMessage
    }

    fun removeLoadingMessage() {
        messages = messages.filterNot { it.isLoading }
    }

    suspend fun sendMessageToAPI(messageText: String): ChatResponse? {
        return try {
            // Validar token antes de enviar
            if (token.isBlank()) {
                Log.e("TelaChatDePrecos", "❌ Token de autenticação não encontrado")
                return null
            }

            Log.d("TelaChatDePrecos", "🚀 Enviando mensagem para API: $messageText")
            Log.d("TelaChatDePrecos", "📝 Token: ${token.take(10)}...")
            Log.d("TelaChatDePrecos", "🆔 SessionId: $sessionId")

            val request = ChatRequest(
                chatId = sessionId,
                message = messageText
            )

            val response: Response<ChatResponse> = withContext(Dispatchers.IO) {
                chatApiService.enviarMensagemChat("Bearer $token", request).execute()
            }

            Log.d("TelaChatDePrecos", "📈 HTTP Status Code: ${response.code()}")
            Log.d("TelaChatDePrecos", "✅ Response Success: ${response.isSuccessful}")

            when (response.code()) {
                200 -> {
                    val body = response.body()
                    Log.d("TelaChatDePrecos", "📦 Response Body: $body")

                    if (body?.sucesso == true) {
                        Log.d("TelaChatDePrecos", "🎉 Resposta da IA recebida: ${body.resposta}")
                        body
                    } else {
                        Log.e("TelaChatDePrecos", "❌ API retornou sucesso=false: $body")
                        null
                    }
                }
                401 -> {
                    Log.e("TelaChatDePrecos", "🔒 Erro de autenticação: Token inválido ou expirado")
                    null
                }
                403 -> {
                    Log.e("TelaChatDePrecos", "🚫 Acesso negado: Usuário sem permissão")
                    null
                }
                404 -> {
                    Log.e("TelaChatDePrecos", "🔍 Endpoint não encontrado: Verifique a URL da API")
                    null
                }
                500 -> {
                    Log.e("TelaChatDePrecos", "🔧 Erro interno do servidor")
                    null
                }
                else -> {
                    val errorBody = response.errorBody()?.string()
                    Log.e("TelaChatDePrecos", "🚨 Erro HTTP ${response.code()}: $errorBody")
                    Log.e("TelaChatDePrecos", "🚨 Error Headers: ${response.headers()}")
                    null
                }
            }
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("TelaChatDePrecos", "⏱️ Timeout na requisição: ${e.message}")
            null
        } catch (_: java.net.UnknownHostException) {
            Log.e("TelaChatDePrecos", "🌐 Erro de conectividade: Verifique sua conexão com a internet")
            null
        } catch (e: java.net.ConnectException) {
            Log.e("TelaChatDePrecos", "🔌 Falha ao conectar com o servidor: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("TelaChatDePrecos", "💥 Exceção geral ao chamar API: ${e.message}", e)
            Log.e("TelaChatDePrecos", "💥 Exception Stack Trace: ${e.stackTraceToString()}")
            null
        }
    }

    fun sendMessage(text: String) {
        if (text.isNotBlank() && !isLoadingResponse) {
            Log.d("TelaChatDePrecos", "💬 Usuário enviou: $text")

            // Adiciona mensagem do usuário
            val newMessage = ChatMessage(text, true, getCurrentTime())
            messages = messages + newMessage
            inputText = ""

            isLoadingResponse = true
            showLoadingMessage()

            coroutineScope.launch {
                try {
                    // Scroll para mostrar mensagem do usuário
                    listState.animateScrollToItem(messages.size - 1)

                    val apiResponse = sendMessageToAPI(text)

                    removeLoadingMessage()

                    if (apiResponse != null) {
                        // Resposta bem-sucedida da API
                        val responseMessage = ChatMessage(
                            text = apiResponse.resposta,
                            isUser = false,
                            time = getCurrentTime()
                        )
                        messages = messages + responseMessage

                        Log.d("TelaChatDePrecos", "✅ Mensagem da IA adicionada: ${apiResponse.resposta}")

                        // Mostrar toast de sucesso (opcional)
                        Toast.makeText(context, "Resposta recebida!", Toast.LENGTH_SHORT).show()

                    } else {
                        // Erro na API - mostrar mensagem de erro específica
                        val errorMessage = when {
                            token.isBlank() -> ChatMessage(
                                text = "Para usar o chat, você precisa estar logado. Por favor, faça login novamente.",
                                isUser = false,
                                time = getCurrentTime(),
                                isError = true,
                                errorMessage = "Token de autenticação ausente"
                            )
                            else -> ChatMessage(
                                text = "Desculpe, não consegui processar sua mensagem no momento. Tente novamente em alguns instantes.",
                                isUser = false,
                                time = getCurrentTime(),
                                isError = true,
                                errorMessage = "Erro na comunicação com a API"
                            )
                        }
                        messages = messages + errorMessage

                        Log.e("TelaChatDePrecos", "❌ Erro ao obter resposta da API")

                        val toastMessage = when {
                            token.isBlank() -> "Faça login para usar o chat"
                            else -> "Erro ao conectar com a IA"
                        }
                        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e("TelaChatDePrecos", "💥 Erro geral no envio da mensagem: ${e.message}", e)

                    removeLoadingMessage()

                    val errorMessage = when (e) {
                        is java.net.SocketTimeoutException -> ChatMessage(
                            text = "A resposta está demorando mais que o esperado. Verifique sua conexão e tente novamente.",
                            isUser = false,
                            time = getCurrentTime(),
                            isError = true,
                            errorMessage = "Timeout na requisição"
                        )
                        is java.net.UnknownHostException -> ChatMessage(
                            text = "Não foi possível conectar com o servidor. Verifique sua conexão com a internet.",
                            isUser = false,
                            time = getCurrentTime(),
                            isError = true,
                            errorMessage = "Erro de conectividade"
                        )
                        is java.net.ConnectException -> ChatMessage(
                            text = "Falha ao conectar com o servidor. Tente novamente mais tarde.",
                            isUser = false,
                            time = getCurrentTime(),
                            isError = true,
                            errorMessage = "Falha de conexão"
                        )
                        else -> ChatMessage(
                            text = "Ops! Ocorreu um erro inesperado. Por favor, tente novamente.",
                            isUser = false,
                            time = getCurrentTime(),
                            isError = true,
                            errorMessage = e.message
                        )
                    }
                    messages = messages + errorMessage

                    Toast.makeText(context, "Erro inesperado: ${e.message}", Toast.LENGTH_LONG).show()

                } finally {
                    isLoadingResponse = false

                    // Scroll para a última mensagem
                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.size - 1)
                    }
                }
            }
        } else if (isLoadingResponse) {
            Log.d("TelaChatDePrecos", "⏳ Aguardando resposta anterior...")
            Toast.makeText(context, "Aguarde a resposta anterior...", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleOptionClick(option: String) {
        sendMessage(option)
        showOptions = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            items(messages) { message ->
                ChatMessageItem(message)
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
                                OptionItem("Comparar preços") { handleOptionClick("Comparar preços") }
                                OptionItem("Comparar lista de compras") { handleOptionClick("Comparar lista de compras") }
                                OptionItem("Dúvidas") { handleOptionClick("Dúvidas") }
                                OptionItem("Como funciona?") { handleOptionClick("Como funciona?") }
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
                onClick = { sendMessage(inputText) },
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
        // Menu inferior
        BottomMenu(navController = navController ?: return, isAdmin = isAdmin)
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isUser) {
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
                        message.isUser -> Color(0xFFF9A01B)
                        message.isError -> Color(0xFFFFE5E5)
                        message.isLoading -> Color(0xFFF0F0F0)
                        else -> Color(0xFFFFF8E7)
                    },
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.time,
                fontSize = 11.sp,
                color = when {
                    message.isUser -> Color.White.copy(alpha = 0.8f)
                    message.isError -> Color.Red.copy(alpha = 0.7f)
                    else -> Color.Gray
                },
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Texto da mensagem com indicador de loading
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    color = when {
                        message.isUser -> Color.White
                        message.isError -> Color.Red
                        else -> Color.Black
                    },
                    lineHeight = 20.sp,
                    modifier = Modifier.weight(1f, fill = false)
                )

                // Indicador de loading
                if (message.isLoading) {
                    Spacer(modifier = Modifier.width(8.dp))
                    // Simulação de pontos pulsantes para loading
                    val alpha by animateFloatAsState(
                        targetValue = 0.3f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        ),
                        label = "loading_alpha"
                    )
                    Text(
                        text = "●●●",
                        fontSize = 12.sp,
                        color = Color.Gray.copy(alpha = alpha),
                    )
                }
            }

            // Mostrar erro detalhado se houver
            if (message.isError && !message.errorMessage.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Erro: ${message.errorMessage}",
                    fontSize = 10.sp,
                    color = Color.Red.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun OptionItem(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date())
}

@Preview(showSystemUi = true)
@Composable
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(null)
    }
}
