package com.example.infohub_telas.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.model.*
import com.example.infohub_telas.ui.theme.*
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoriosEmpresariaisScreen(
    navController: NavController,
    companyId: String? = null,
    modifier: Modifier = Modifier
) {
    val metrics = CompanyMockData.sampleMetrics
    var selectedCompany by remember { mutableStateOf<Company?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Relatórios Empresariais") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Summary Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SummaryCard(
                        icon = Icons.Default.Business,
                        title = "Total de Empresas",
                        value = "${metrics.totalCompanies}",
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        icon = Icons.Default.CheckCircle,
                        title = "Empresas Ativas",
                        value = "${metrics.activeCompanies}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SummaryCard(
                        icon = Icons.Default.LocalOffer,
                        title = "Promoções",
                        value = "${metrics.totalPromotions}",
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        icon = Icons.Default.AttachMoney,
                        title = "Faturamento Médio",
                        value = "R$ ${String.format("%.2f", metrics.averageRevenue)}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Chart
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            "Distribuição de Faturamento",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                        RevenueChart(
                            companies = metrics.companiesData,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp)
                        )
                    }
                }
            }

            // Company Performance Section
            item {
                Text(
                    "Desempenho por Empresa",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(metrics.companiesData) { company ->
                CompanyPerformanceCard(
                    company = company,
                    onDetailsClick = { selectedCompany = company }
                )
            }

            item {
                Button(
                    onClick = { /* Implement report generation */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Icon(Icons.Default.Description, "Gerar Relatório")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gerar Novo Relatório")
                }
            }
        }

        // Company Details Modal
        selectedCompany?.let { company ->
            AlertDialog(
                onDismissRequest = { selectedCompany = null },
                title = { Text(company.name) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DetailRow("Faturamento", "R$ ${String.format("%.2f", company.revenue)}")
                        DetailRow("Promoções", "${company.promotionsCount}")
                        DetailRow("Vendas", "${company.salesCount}")
                        DetailRow("Avaliação", "${company.rating}/5.0")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedCompany = null }) {
                        Text("Fechar")
                    }
                }
            )
        }
    }
}

@Composable
fun SummaryCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
            Text(
                value,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyPerformanceCard(
    company: Company,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onDetailsClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    company.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "R$ ${String.format("%.2f", company.revenue)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusChip(status = company.status)
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RevenueChart(
    companies: List<Company>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val maxRevenue = companies.maxOfOrNull { it.revenue } ?: 0.0
        val barWidth = (size.width - 40f) / companies.size
        val maxHeight = size.height - 40f

        companies.forEachIndexed { index, company ->
            val barHeight = ((company.revenue / maxRevenue) * maxHeight).toFloat()
            val x = 20f + (index * barWidth)
            val y = size.height - 20f

            // Draw bar
            drawBar(
                x = x,
                y = y,
                width = barWidth - 10f,
                height = barHeight,
                color = when (company.status) {
                    CompanyStatus.ACTIVE -> ChartGreen
                    CompanyStatus.PENDING -> ChartOrange
                    CompanyStatus.INACTIVE -> StatusInactive
                }
            )
        }
    }
}

private fun DrawScope.drawBar(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color
) {
    drawRect(
        color = color.copy(alpha = 0.6f),
        topLeft = Offset(x, y - height),
        size = androidx.compose.ui.geometry.Size(width, height)
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RelatoriosEmpresariaisScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RelatoriosEmpresariaisScreen(
                navController = rememberNavController(),
                companyId = "1"
            )
        }
    }
}
