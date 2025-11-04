package com.example.infohub_telas.telas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.*
import com.example.infohub_telas.model.PeriodoRelatorio
import com.example.infohub_telas.model.Relatorio
import com.example.infohub_telas.model.RelatorioMetrics
import com.example.infohub_telas.model.RelatorioMockData
import com.example.infohub_telas.model.TipoRelatorio
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoriosScreen(
    navController: NavController,
    empresaId: String? = null
) {
    var currentFilter by remember { mutableStateOf(TipoRelatorio.DESEMPENHO) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showPeriodoDialog by remember { mutableStateOf(false) }
    var selectedPeriodo by remember { mutableStateOf(PeriodoRelatorio.MENSAL) }
    var showGerarRelatorioDialog by remember { mutableStateOf(false) }

    val metrics = remember { RelatorioMockData.sampleMetrics }
    val relatorios = remember { RelatorioMockData.sampleRelatorios }
    val filteredRelatorios = remember(currentFilter) {
        relatorios.filter { it.tipo == currentFilter }
    }

    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Relatórios do Sistema",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
            ) {
                // Filtro
                IconButton(onClick = { showFilterDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtrar",
                        tint = Color.White
                    )
                }
                // Período
                IconButton(onClick = { showPeriodoDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Período",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Filtros horizontais
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TipoRelatorio.values()) { tipo ->
                    FilterChip(
                        selected = currentFilter == tipo,
                        onClick = { currentFilter = tipo },
                        label = { Text(tipo.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryOrange,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Estatísticas
                item {
                    Text(
                        text = "Visão Geral",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Cards de Resumo em grid 2x2
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DashboardStatisticCard(
                            icon = Icons.Default.Business,
                            title = "Empresas",
                            value = "${metrics.totalEmpresas}",
                            subvalue = "${metrics.empresasAtivas} ativas",
                            backgroundColor = Color(0xFF2196F3),
                            modifier = Modifier.weight(1f)
                        )
                        DashboardStatisticCard(
                            icon = Icons.Default.TrendingUp,
                            title = "Crescimento",
                            value = "${String.format("%.1f", metrics.taxaCrescimento)}%",
                            subvalue = "último período",
                            backgroundColor = Color(0xFF4CAF50),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DashboardStatisticCard(
                            icon = Icons.Default.Group,
                            title = "Usuários",
                            value = "${metrics.usuariosAtivos}",
                            subvalue = "ativos no sistema",
                            backgroundColor = Color(0xFFFF9800),
                            modifier = Modifier.weight(1f)
                        )
                        DashboardStatisticCard(
                            icon = Icons.Default.AttachMoney,
                            title = "Faturamento",
                            value = currencyFormatter.format(metrics.faturamentoTotal),
                            subvalue = "total acumulado",
                            backgroundColor = Color(0xFF9C27B0),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Título da seção de relatórios
                item {
                    Text(
                        text = "Relatórios ${currentFilter.name}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                // Lista de relatórios filtrados
                items(filteredRelatorios) { relatorio ->
                    com.example.infohub_telas.components.RelatorioCard(
                        relatorio = relatorio,
                        onVisualizarClick = {
                            navController.navigate("detalhesRelatorio/${relatorio.id}")
                        },
                        onExportarClick = { /* Implementar exportação */ },
                        onCompartilharClick = { /* Implementar compartilhamento */ }
                    )
                }

                // Botão Gerar Novo Relatório
                item {
                    Button(
                        onClick = { showGerarRelatorioDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Gerar Novo Relatório")
                    }
                }
            }
        }

        // Dialog de Filtros
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtrar Relatórios") },
                text = {
                    Column {
                        Text("Implementar filtros adicionais")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Aplicar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Dialog de Período
        if (showPeriodoDialog) {
            AlertDialog(
                onDismissRequest = { showPeriodoDialog = false },
                title = { Text("Selecionar Período") },
                text = {
                    Column {
                        PeriodoRelatorio.values().forEach { periodo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedPeriodo = periodo
                                        showPeriodoDialog = false
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedPeriodo == periodo,
                                    onClick = {
                                        selectedPeriodo = periodo
                                        showPeriodoDialog = false
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(periodo.name)
                            }
                        }
                    }
                },
                confirmButton = { }
            )
        }

        // Dialog de Geração de Relatório
        if (showGerarRelatorioDialog) {
            AlertDialog(
                onDismissRequest = { showGerarRelatorioDialog = false },
                title = { Text("Gerar Novo Relatório") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Tipo de Relatório
                        OutlinedTextField(
                            value = currentFilter.name,
                            onValueChange = { },
                            label = { Text("Tipo de Relatório") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Período
                        OutlinedTextField(
                            value = selectedPeriodo.name,
                            onValueChange = { },
                            label = { Text("Período") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Empresa (se aplicável)
                        if (empresaId != null) {
                            OutlinedTextField(
                                value = "Empresa Selecionada",
                                onValueChange = { },
                                label = { Text("Empresa") },
                                enabled = false,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            /* Implementar geração */
                            showGerarRelatorioDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                    ) {
                        Text("Gerar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showGerarRelatorioDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
private fun GraficoDesempenho() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Desempenho Mensal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                val valores = listOf(0.6f, 0.4f, 0.8f, 0.5f, 0.7f, 0.9f)

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val barWidth = canvasWidth / 7f
                    val maxHeight = canvasHeight * 0.8f

                    // Grade horizontal
                    for (i in 0..4) {
                        val y = canvasHeight * i / 4
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(x = 0f, y = y),
                            end = Offset(x = canvasWidth, y = y),
                            strokeWidth = 1f
                        )
                    }

                    // Barras verticais
                    valores.forEachIndexed { index, valor ->
                        val barHeight = maxHeight * valor
                        val x = index * barWidth + barWidth / 3
                        val startY = canvasHeight
                        val endY = canvasHeight - barHeight

                        drawRect(
                            color = PrimaryOrange,
                            topLeft = Offset(x = x - barWidth/4, y = endY),
                            size = Size(width = barWidth/2, height = barHeight),
                            alpha = 0.7f
                        )
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RelatoriosScreenPreview() {
    InfoHub_telasTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RelatoriosScreen(rememberNavController())
        }
    }
}
