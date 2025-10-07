package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.model.ChatRequest
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send


@Composable
fun TelaChatDePrecos(navController: NavController?) {
    val mensagens = remember { mutableStateListOf("Olá! Em que posso te ajudar hoje?") }
    var mensagemUsuario by remember { mutableStateOf("") }

    // Suponha que você já tenha sua instância criada assim:
    val chatService = RetrofitFactory().getInfoHub_UserService()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Assistente InfoHub",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Lista de mensagens
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            mensagens.forEach { msg ->
                val isUsuario = msg.startsWith("Você:")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = if (isUsuario) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        text = msg,
                        modifier = Modifier
                            .background(
                                if (isUsuario) Color(0xFF25992E) else Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        color = if (isUsuario) Color.White else Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Campo de texto + botão enviar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = mensagemUsuario,
                onValueChange = { mensagemUsuario = it },
                placeholder = { Text("Digite sua mensagem") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            IconButton(onClick = {
                if (mensagemUsuario.isNotBlank()) {
                    val texto = mensagemUsuario
                    mensagens.add("Você: $texto")
                    mensagemUsuario = ""

                    GlobalScope.launch(Dispatchers.IO) {
                        val token = "Bearer SEU_TOKEN_AQUI"  // 🔐 coloque o token certo aqui

                        val call = chatService.enviarMensagemChat(
                            token,
                            ChatRequest(chatId = "sessao-teste", message = texto)
                        )
                        val response = call.execute()

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val resposta = response.body()?.resposta ?: "Sem resposta"
                                mensagens.add("Bot: $resposta")
                            } else {
                                mensagens.add("Bot: Erro ao enviar")
                            }
                        }
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Enviar",
                    tint = Color(0xFF25992E)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(null)
    }
}
