package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@Composable
fun TelaChatDePrecos(navController: NavController?) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Título principal
        Text(
            text = "Chat de Preços IA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Mensagem de boas-vindas
        Text(
            text = "Olá usuário, seja bem-vindo!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "Selecione a opção que deseja escolher:",
            fontSize = 16.sp,
            color = Color(0xFF222222),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Botão "Abrir opções"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFC107), shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📋  Abrir opções",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de opções
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFC107), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Selecione a opção que deseja escolher:",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "1. Comparar preços", color = Color.White, fontSize = 14.sp)
                Text(text = "2. Comparar lista de compras", color = Color.White, fontSize = 14.sp)
                Text(text = "3. Dúvidas", color = Color.White, fontSize = 14.sp)
                Text(text = "4. Como funciona?", color = Color.White, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto + botão enviar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F6F6), RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                decorationBox = { innerTextField ->
                    if (inputText.isEmpty()) {
                        Text(
                            text = "Digite o produto que você procura...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )

            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Enviar",
                tint = Color(0xFF43A047)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Barra "Ver carrinho"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Ver carrinho",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "R$ 0,00",
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Menu inferior (com ícones reais, sem função separada)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone + texto - Início
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Início",
                    modifier = Modifier.size(28.dp)
                )
                Text("Início", fontSize = 12.sp, color = Color.Black)
            }

            // Ícone + texto - Promoções
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_promocoes),
                    contentDescription = "Promoções",
                    modifier = Modifier.size(28.dp)
                )
                Text("Promoções", fontSize = 12.sp, color = Color.Black)
            }

            // Ícone + texto - Localização
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_localizacao),
                    contentDescription = "Localização",
                    modifier = Modifier.size(28.dp)
                )
                Text("Localização", fontSize = 12.sp, color = Color.Black)
            }

            // Ícone + texto - InfoCash
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_infocash),
                    contentDescription = "InfoCash",
                    modifier = Modifier.size(28.dp)
                )
                Text("InfoCash", fontSize = 12.sp, color = Color.Black)
            }

            // Ícone + texto - Meu Perfil
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Meu Perfil",
                    modifier = Modifier.size(28.dp)
                )
                Text("Meu Perfil", fontSize = 12.sp, color = Color.Black)
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
