package com.example.infohub_telas.telas

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.*
import com.example.infohub_telas.ui.theme.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesRelatorioScreen(
    navController: NavController,
    relatorioId: String
) {
    var showShareDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    // Simulando dados do relatório
    val relatorio = remember {
        RelatorioMockData.sampleRelatorios.find { it.id == relatorioId }
            ?: RelatorioMockData.sampleRelatorios.first()
    }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")) }
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalhes do Relatório",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
            ) {
                // Filtro
                IconButton(onClick = { showFilterDialog = true }) {
                    Icon(Icons.Default.FilterList, "Filtrar", tint = Color.White)
                }
                // Comentário
                IconButton(onClick = { showCommentDialog = true }) {
                    Icon(Icons.Default.Comment, "Comentar", tint = Color.White)
                }
                // Compartilhar
                IconButton(onClick = { showShareDialog = true }) {
                    Icon(Icons.Default.Share, "Compartilhar", tint = Color.White)
                }
                // Exportar
                IconButton(onClick = { showExportDialog = true }) {
                    Icon(Icons.Default.Download, "Exportar", tint = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cabeçalho do Relatório
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
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

                        // Chips de informação
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoChip(
                                icon = Icons.Default.CalendarToday,
                                text = dateFormat.format(relatorio.dataGeracao)
                            )
                            InfoChip(
                                icon = Icons.Default.Person,
                                text = relatorio.autor
                            )
                            InfoChip(
                                icon = Icons.Default.Category,
                                text = relatorio.tipo.name,
                                color = PrimaryOrange
                            )
                        }
                    }
                }
            }

            // KPIs Principais
            item {
                Text(
                    "Indicadores Principais",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    relatorio.metricas.entries.take(3).forEach { (key, value) ->
                        KPICard(
                            title = key,
                            value = value,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Gráfico Interativo
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Evolução Temporal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { /* Alternar visualização */ }) {
                                Icon(Icons.Default.BarChart, "Alternar gráfico")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp)
                        ) {
                            // Aqui seria implementado o gráfico real
                            Text(
                                "Gráfico Interativo",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            // Detalhamento
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            "Análise Detalhada",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            relatorio.descricao,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Tags e Categorização
            item {
                if (relatorio.tags.isNotEmpty()) {
                    Text(
                        "Categorização",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(relatorio.tags) { tag ->
                            TagChip(tag = tag)
                        }
                    }
                }
            }

            // Botões de Ação
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { showShareDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("COMPARTILHAR")
                    }
                    Button(
                        onClick = { showExportDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                    ) {
                        Icon(Icons.Default.Download, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EXPORTAR")
                    }
                }
            }
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
                            headlineContent = { Text("Gerar Link") },
                            leadingContent = { Icon(Icons.Default.Link, null) },
                            modifier = Modifier.clickable { /* Implementar */ }
                        )
                        ListItem(
                            headlineContent = { Text("Compartilhar PDF") },
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
        if (showExportDialog) {
            AlertDialog(
                onDismissRequest = { showExportDialog = false },
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
                    TextButton(onClick = { showExportDialog = false }) {
                        Text("Fechar")
                    }
                }
            )
        }

        // Diálogo de Comentário
        if (showCommentDialog) {
            AlertDialog(
                onDismissRequest = { showCommentDialog = false },
                title = { Text("Adicionar Comentário") },
                text = {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("Seu comentário") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 5
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showCommentDialog = false }) {
                        Text("Enviar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCommentDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo de Filtros
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtrar Dados") },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("Período") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Icon(Icons.Default.CalendarToday, null)
                            }
                        )
                        OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("Categoria") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, null)
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Mostrar Detalhes")
                            Switch(
                                checked = true,
                                onCheckedChange = { }
                            )
                        }
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
    }
}

@Composable
private fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

@Composable
private fun KPICard(
    title: String,
    value: Double,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = String.format("%.1f%%", value),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if (value >= 0) PrimaryOrange else Color.Red
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TagChip(tag: String) {
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun DetalhesRelatorioScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetalhesRelatorioScreen(
                navController = rememberNavController(),
                relatorioId = "1"
            )
        }
    }
}
