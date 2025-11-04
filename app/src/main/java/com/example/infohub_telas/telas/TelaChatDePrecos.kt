package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenuWithCart
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.primaryLight

@Composable
fun TelaChatDePrecos(navController: NavController?) {
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            if (navController != null) {
                BottomMenuWithCart(navController = navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

            // Conteúdo principal com scroll e centralização
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Subtítulo
                Text(
                    text = "Compare preços instantaneamente com nossa inteligência artificial",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF9A01B),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                )

                // Mensagem do robô
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(80.dp)
                            .background(Color(0xFFF9A01B))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.robo),
                        contentDescription = "Robo",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFFFF3E0), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "16:12",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF9A01B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Olá! Sou sua assistente de compras inteligente. Posso ajudar você a encontrar os melhores preços de qualquer produto. Digite o nome do produto que você procura!",
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 20.sp
                        )
                    }
                }

                // Botão abrir opções
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp)
                        .background(Color(0xFFF9A01B), RoundedCornerShape(12.dp))
                        .height(56.dp)
                        .clickable { /* Ação do botão */ },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Menu",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Abrir opções",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Lista de opções
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                        .background(Color(0xFFFFF3E0), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Selecione a opção que deseja escolher:",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "1. Comparar preços", color = Color.Black, fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "2. Comparar lista de compras", color = Color.Black, fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "3. Dúvidas", color = Color.Black, fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "4. Como funciona?", color = Color.Black, fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }

                // Spacer flexível para empurrar o campo de input para baixo
                Spacer(modifier = Modifier.weight(1f))

                // Campo de input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .background(Color(0xFFF6F6F6), RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                        decorationBox = { innerTextField ->
                            if (inputText.isEmpty()) {
                                Text(
                                    text = "Digite o produto que você procura...",
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = Color(0xFF43A047),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { /* Ação de enviar */ }
                    )
                }

                // Barra "Ver carrinho" - REMOVIDA porque agora está no bottomBar
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(rememberNavController())
    }
}