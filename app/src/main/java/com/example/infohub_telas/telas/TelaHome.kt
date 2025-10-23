package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

// Paleta de cores
val Laranja = Color(0xFFF9A01B)
val Verde = Color(0xFF25992E)
val Vermelho = Color(0xFFF06339)
val CinzaFundo = Color(0xFFF8F8F8)
val CinzaClaro = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController: NavController?) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = CinzaFundo,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.jara),
                            contentDescription = "Logo InfoHub",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "INFOHUB",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Laranja)
            )
        },
        bottomBar = { BottomMenu(navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Banner promocional
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Laranja),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Banner Promocional",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Indicador de banner (bolinhas)
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (index == 0) Laranja else CinzaClaro,
                                    CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Barra de busca
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO: Implement search functionality */ },
                    placeholder = { Text("Buscar produtos...", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.lupa_loc),
                            contentDescription = "Buscar",
                            tint = Laranja,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.microfone_loc),
                            contentDescription = "Busca por voz",
                            tint = Laranja,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Seção de Promoções
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Promoções",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ver mais",
                                    fontSize = 14.sp,
                                    color = Laranja,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.seta_direita),
                                    contentDescription = "Ver mais",
                                    tint = Laranja,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(3) { index ->
                                CardProduto()
                            }
                        }

                        // Indicador de scroll
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(12) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .padding(horizontal = 2.dp)
                                        .background(
                                            if (index < 3) Laranja else CinzaClaro,
                                            CircleShape
                                        )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Seção de Ranking Semanal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Ranking Semanal",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Top usuários da semana",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Lista de usuários no ranking - mostra apenas o primeiro
                        ItemRanking(
                            posicao = 1,
                            nome = "Israel Magalhães Dos Santos",
                            pontos = "248 IC",
                            corMedalha = Laranja,
                            isLast = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            // Botão flutuante de chat posicionado no canto inferior direito
            FloatingActionButton(
                onClick = { navController.navigate("chat_precos") },
                containerColor = Laranja,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 80.dp)
                    .size(64.dp)
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.robo),
                    contentDescription = "Chat de Preços",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun CardProduto() {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tag "Oferta"
            Box(
                modifier = Modifier
                    .background(Verde, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Oferta",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Imagem do produto (placeholder)
            Image(
                painter = painterResource(id = R.drawable.jara),
                contentDescription = "Produto",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Desconto
            Box(
                modifier = Modifier
                    .background(Laranja, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "-25%",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Preço
            Text(
                text = "R$ 7,99",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Verde
            )

            // Botão adicionar
            IconButton(
                onClick = {},
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.adicionar),
                    contentDescription = "Adicionar",
                    tint = Vermelho,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ItemRanking(
    posicao: Int,
    nome: String,
    pontos: String,
    corMedalha: Color,
    isLast: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "#$posicao",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Laranja,
                    modifier = Modifier.width(40.dp)
                )

                // Medalha
                if (posicao <= 3) {
                    Text(
                        text = "🏆",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                } else {
                    Text(
                        text = "👤",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                // Nome
                Text(
                    text = nome,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }

            // Pontos
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pontos,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Laranja,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.cash_menu),
                    contentDescription = "InfoCash",
                    tint = Laranja,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (!isLast) {
            HorizontalDivider(
                color = CinzaClaro,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaHomePreview() {
    InfoHub_telasTheme {
        TelaHome(rememberNavController())
    }
}