package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.primaryLight

@Composable
fun TelaChatDePrecos(navController: NavController?) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(primaryLight)
        ) {

            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 16.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Início",
                    modifier = Modifier
                        .width(60.dp)
                        .height(70.dp)
                )

            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp) // ajuste o padding conforme precisar
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = "Voltar",
                tint = Color(0xFFF9A01B)
            )
            Spacer(modifier = Modifier.width(8.dp)) // espaço entre o ícone e o texto
            Text(
                text = "Chat de Preços IA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF9A01B)
            )

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp)
        ) {
            Text(
                text = "Compare preços instantaneamente com nossa inteligência artificial",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF9A01B)
            )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(Color(0xFFFFC107))
                        .padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.robo),
                    contentDescription = "robo",
                    tint = Color(0xFFF9A01B),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)

                )
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFF3E0),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)

                        ) {
                            Text(
                                text = "16:12",
                                fontSize = 12.sp,
                                color = Color(0xFFF9A01B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Olá! Sou sua assistente de compras inteligente. Posso ajudar você a encontrar os melhores preços de qualquer produto. Digite o nome do produto que você procura!",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFFC107), shape = RoundedCornerShape(12.dp))
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Abrir opções",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))


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
                                            painter = painterResource(id = R.drawable.inicio),
                                            contentDescription = "Início",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text("Início", fontSize = 12.sp, color = Color.Black)
                                    }

                                    // Ícone + texto - Promoções
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = R.drawable.promocoes),
                                            contentDescription = "Promoções",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text("Promoções", fontSize = 12.sp, color = Color.Black)
                                    }

                                    // Ícone + texto - Localização
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = R.drawable.local),
                                            contentDescription = "Localização",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text("Localização", fontSize = 12.sp, color = Color.Black)
                                    }

                                    // Ícone + texto - InfoCash
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = R.drawable.dinheiro),
                                            contentDescription = "InfoCash",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text("InfoCash", fontSize = 12.sp, color = Color.Black)
                                    }

                                    // Ícone + texto - Meu Perfil
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = R.drawable.perfil),
                                            contentDescription = "Meu Perfil",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text("Meu Perfil", fontSize = 12.sp, color = Color.Black)
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
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(null)
    }
}
