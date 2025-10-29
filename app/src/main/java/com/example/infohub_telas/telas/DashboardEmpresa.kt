package com.example.infohub_telas.telas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.components.DashboardStatisticCard
import com.example.infohub_telas.components.PromocaoListItem
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.model.DashboardData
import com.example.infohub_telas.model.Promocao
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange
import com.example.infohub_telas.ui.theme.SecondaryOrange
import com.example.infohub_telas.ui.theme.BackgroundGray
import java.time.LocalDate
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardEmpresa(
    navController: NavController,
    dashboardData: DashboardData
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Dashboard",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGray),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estatísticas em cards
            item {
                Grid4Cards(dashboardData)
            }

            // Gráfico de Desempenho
            item {
                ChartSection()
            }

            // Seção de Últimas Promoções
            item {
                Text(
                    text = "Últimas Promoções",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(dashboardData.ultimasPromocoes) { promocao ->
                PromocaoListItem(
                    promocao = promocao,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Botão Ver Todas
            item {
                Button(
                    onClick = { /* Navegar para lista completa */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryOrange
                    )
                ) {
                    Text("Ver Todas as Promoções")
                }
            }
        }
    }
}

@Composable
private fun Grid4Cards(data: DashboardData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DashboardStatisticCard(
                icon = Icons.Default.ShoppingCart,
                title = "Vendas do Mês",
                value = data.getFormattedVendas(),
                backgroundColor = Color(0xFF2196F3),
                modifier = Modifier.weight(1f)
            )
            DashboardStatisticCard(
                icon = Icons.Default.LocalOffer,
                title = "Promoções Ativas",
                value = "${data.totalPromocoesAtivas}",
                backgroundColor = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DashboardStatisticCard(
                icon = Icons.Default.Inventory,
                title = "Produtos",
                value = "${data.totalProdutos}",
                backgroundColor = Color(0xFFF44336),
                modifier = Modifier.weight(1f)
            )
            DashboardStatisticCard(
                icon = Icons.Default.Star,
                title = "Avaliação Média",
                value = data.getFormattedAvaliacao(),
                backgroundColor = Color(0xFFFF9800),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ChartSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Desempenho de Vendas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                // Simulação de gráfico com Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val points = List(6) {
                        Pair(
                            it * (size.width / 5),
                            size.height * (0.2 + Random.nextDouble(0.6))
                        )
                    }

                    val path = Path()
                    path.moveTo(0f, size.height)
                    points.forEachIndexed { index, (x, y) ->
                        if (index == 0) {
                            path.lineTo(x.toFloat(), y.toFloat())
                        } else {
                            path.lineTo(x.toFloat(), y.toFloat())
                        }
                    }

                    // Desenhar linhas de grade
                    for (i in 0..5) {
                        drawLine(
                            Color.LightGray,
                            start = Offset(0f, size.height * i / 5),
                            end = Offset(size.width, size.height * i / 5),
                            strokeWidth = 1f
                        )
                    }

                    // Desenhar linha do gráfico
                    for (i in 0 until points.size - 1) {
                        drawLine(
                            Color(0xFF2196F3),
                            start = Offset(points[i].first.toFloat(), points[i].second.toFloat()),
                            end = Offset(points[i + 1].first.toFloat(), points[i + 1].second.toFloat()),
                            strokeWidth = 3f
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun DashboardEmpresaPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DashboardEmpresa(
                navController = rememberNavController(),
                dashboardData = DashboardData.getPreview()
            )
        }
    }
}
