package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.*
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizarRelatorioScreen(
    navController: NavController,
    relatorioId: String? = null
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")) }
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    // Simular dados do relatório
    val relatorio = remember {
        RelatorioMockData.sampleRelatorios.firstOrNull { it.id == relatorioId }
            ?: RelatorioMockData.sampleRelatorios.first()
    }

    var showShareDialog by remember { mutableStateOf(false) }
    var showExportOptions by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Visualizar Relatório",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { showShareDialog = true }) {
                        Icon(Icons.Default.Share, "Compartilhar", tint = Color.White)
                    }
                    IconButton(onClick = { showExportOptions = true }) {
                        Icon(Icons.Default.Download, "Exportar", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Cabeçalho do Relatório
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = relatorio.nome,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ChipInfo(
                            icon = Icons.Default.DateRange,
                            text = dateFormat.format(relatorio.dataGeracao)
                        )
                        ChipInfo(
                            icon = Icons.Default.Person,
                            text = relatorio.autor
                        )
                        Surface(
                            color = PrimaryOrange.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = relatorio.tipo.name,
                                color = PrimaryOrange,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }

            // Resumo Executivo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Resumo Executivo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = relatorio.descricao,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Métricas Principais
            if (relatorio.metricas.isNotEmpty()) {
                Text(
                    text = "Principais Indicadores",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    relatorio.metricas.entries.take(3).forEach { (key, value) ->
                        MetricaCard(
                            titulo = key,
                            valor = value,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Gráfico (Placeholder)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Gráfico de Desempenho")
                }
            }

            // Tags
            if (relatorio.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    relatorio.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = tag,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Diálogo de Compartilhamento
        if (showShareDialog) {
            AlertDialog(
                onDismissRequest = { showShareDialog = false },
                title = { Text("Compartilhar Relatório") },
                text = {
                    Column {
                        ListItem(
                            headlineContent = { Text("Enviar por E-mail") },
                            leadingContent = { Icon(Icons.Default.Email, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                        ListItem(
                            headlineContent = { Text("Compartilhar Link") },
                            leadingContent = { Icon(Icons.Default.Link, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                        ListItem(
                            headlineContent = { Text("Exportar PDF") },
                            leadingContent = { Icon(Icons.Default.PictureAsPdf, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showShareDialog = false }) {
                        Text("Fechar")
                    }
                }
            )
        }

        // Diálogo de Exportação
        if (showExportOptions) {
            AlertDialog(
                onDismissRequest = { showExportOptions = false },
                title = { Text("Exportar Relatório") },
                text = {
                    Column {
                        ListItem(
                            headlineContent = { Text("PDF") },
                            supportingContent = { Text("Documento em alta qualidade") },
                            leadingContent = { Icon(Icons.Default.PictureAsPdf, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                        ListItem(
                            headlineContent = { Text("Excel") },
                            supportingContent = { Text("Planilha editável") },
                            leadingContent = { Icon(Icons.Default.TableView, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                        ListItem(
                            headlineContent = { Text("Power BI") },
                            supportingContent = { Text("Dashboard interativo") },
                            leadingContent = { Icon(Icons.Default.BarChart, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showExportOptions = false }) {
                        Text("Fechar")
                    }
                }
            )
        }
    }
}

@Composable
private fun ChipInfo(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MetricaCard(
    titulo: String,
    valor: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = String.format("%.1f%%", valor),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (valor >= 0) PrimaryOrange else Color.Red
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun VisualizarRelatorioScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            VisualizarRelatorioScreen(
                navController = rememberNavController()
            )
        }
    }
}
