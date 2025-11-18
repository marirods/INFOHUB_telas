package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaGanharHubCoins(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar com gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(PrimaryOrange, SecondaryOrange)
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Logo do InfoCash
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "💰",
                                fontSize = 24.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ganhar HubCoins",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Escolha como ganhar pontos",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Conteúdo principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Título
                Text(
                    text = "Como você quer ganhar HubCoins?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Escolha uma das opções abaixo para começar a acumular pontos",
                    fontSize = 14.sp,
                    color = OnSurfaceGray.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Opção 1: Comprar Produtos
                OpcaoGanharCard(
                    icon = Icons.Default.ShoppingCart,
                    titulo = "Comprar Produtos",
                    descricao = "Ganhe HubCoins a cada compra realizada no InfoHub",
                    pontos = "5 pontos por R$ 10",
                    corIcone = PrimaryOrange,
                    onClick = {
                        navController.navigate(Routes.HOME)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Opção 2: Avaliar Mercados
                OpcaoGanharCard(
                    icon = Icons.Default.Star,
                    titulo = "Avaliar Mercados",
                    descricao = "Avalie estabelecimentos e ganhe HubCoins por cada avaliação",
                    pontos = "10 pontos por avaliação",
                    corIcone = Color(0xFFFFA726),
                    onClick = {
                        navController.navigate(Routes.HOME)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Informação adicional
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "💡",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Dica: Quanto mais você participa, mais HubCoins você acumula!",
                            fontSize = 12.sp,
                            color = Color(0xFF5D4037),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpcaoGanharCard(
    icon: ImageVector,
    titulo: String,
    descricao: String,
    pontos: String,
    corIcone: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone
            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(32.dp),
                color = corIcone.copy(alpha = 0.1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = titulo,
                        tint = corIcone,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Conteúdo
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceGray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = descricao,
                    fontSize = 12.sp,
                    color = OnSurfaceGray.copy(alpha = 0.7f),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Badge de pontos
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = corIcone.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = pontos,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = corIcone,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Seta para indicar navegação
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ir",
                tint = OnSurfaceGray.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(24.dp)
                    .then(Modifier.padding(start = 8.dp))
            )
        }
    }
}

