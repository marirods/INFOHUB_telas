package com.example.infohub_telas.telas

import android.R.attr.onClick
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.primaryLight





data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val time: String
)

@Composable
fun TelaChatDePrecos(navController: NavController?) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    var inputText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }
    var messages by remember { mutableStateOf(listOf(
        ChatMessage(
            text = "Olá! Sou sua assistente de compras inteligente. Posso ajudar você a encontrar os melhores preços de qualquer produto. Digite o nome do produto que você procura!",
            isUser = false,
            time = getCurrentTime()
        )
    )) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    fun sendMessage(text: String) {
        if (text.isNotBlank()) {
            val newMessage = ChatMessage(text, true, getCurrentTime())
            messages = messages + newMessage
            inputText = ""
            
            // Simular resposta da IA após 1 segundo
            coroutineScope.launch {
                kotlinx.coroutines.delay(1000)
                val response = ChatMessage(
                    text = "Estou buscando os melhores preços de '$text' para você. Por favor, aguarde...",
                    isUser = false,
                    time = getCurrentTime()
                )
                messages = messages + response
                coroutineScope.launch {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
            
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
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
                .height(80.dp) // Aumentei a altura do header
                .background(primaryLight),
            contentAlignment = Alignment.CenterStart // Alinhamento centralizado verticalmente
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
                    imageVector = Icons.Default.Send,
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
                    color = if (message.isUser) Color(0xFFF9A01B) else Color(0xFFFFF8E7),
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
                color = if (message.isUser) Color.White.copy(alpha = 0.8f) else Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = message.text,
                fontSize = 14.sp,
                color = if (message.isUser) Color.White else Color.Black,
                lineHeight = 20.sp
            )
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
