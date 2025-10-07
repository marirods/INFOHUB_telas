package com.example.infohub_telas.telas

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
                    .padding(top = 20.dp) // Adiciona padding no topo para descer o conteúdo
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp) // Ajuste o tamanho conforme necessário
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
                .padding(top = 24.dp), // Adiciona espaço no topo para descer o conteúdo
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
                    .padding(bottom = 24.dp) // Mais espaço após o subtítulo
            )

            // Mensagem do robô
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp), // Mais espaço após a mensagem
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(80.dp) // Aumentei a altura da linha
                        .background(Color(0xFFFFC107))
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
                        .padding(16.dp) // Aumentei o padding interno
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
                        fontSize = 14.sp, // Aumentei o tamanho da fonte
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
                    .padding(bottom = 20.dp) // Espaço após o botão
                    .background(Color(0xFFFFC107), RoundedCornerShape(12.dp))
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
                    .padding(bottom = 24.dp) // Mais espaço após a lista
                    .background(Color(0xFFFFC107), RoundedCornerShape(12.dp))
                    .padding(16.dp) // Padding interno aumentado
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

            // Barra "Ver carrinho"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFF9A01B), RoundedCornerShape(13.dp))
                    .height(60.dp)
                    .clickable { /* Navegar para carrinho */ },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.sacola),
                            contentDescription = "Carrinho",
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ver carrinho",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        text = "R$0,00",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Menu inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuItem(icon = R.drawable.inicio, label = "Início")
                MenuItem(icon = R.drawable.promocoes, label = "Promoções")
                MenuItem(icon = R.drawable.local, label = "Localização")
                MenuItem(icon = R.drawable.dinheiro, label = "InfoCash")
                MenuItem(icon = R.drawable.perfil, label = "Meu Perfil")
            }
        }
    }
}
@Composable
fun MenuItem(icon: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}


    @Preview(showSystemUi = true)
@Composable
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(null)
    }
}
