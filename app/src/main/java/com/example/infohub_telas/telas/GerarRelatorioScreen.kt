package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.PeriodoRelatorio
import com.example.infohub_telas.model.TipoRelatorio
import com.example.infohub_telas.ui.theme.PrimaryOrange
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import java.text.SimpleDateFormat
import java.util.*

data class RelatorioTemplate(
    val id: String,
    val nome: String,
    val descricao: String,
    val tipo: TipoRelatorio
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerarRelatorioScreen(
    navController: NavController,
    empresaId: String? = null
) {
    var tipoRelatorio by remember { mutableStateOf(TipoRelatorio.DESEMPENHO) }
    var periodoRelatorio by remember { mutableStateOf(PeriodoRelatorio.MENSAL) }
    var dataInicio by remember { mutableStateOf(Date()) }
    var dataFim by remember { mutableStateOf(Date()) }
    var showTipoDialog by remember { mutableStateOf(false) }
    var showPeriodoDialog by remember { mutableStateOf(false) }
    var showTemplateDialog by remember { mutableStateOf(false) }
    var incluirGraficos by remember { mutableStateOf(true) }
    var incluirIndicadoresChave by remember { mutableStateOf(true) }
    var incluirComparativoAnual by remember { mutableStateOf(false) }
    var incluirPrevisoes by remember { mutableStateOf(false) }
    var formatoExportacao by remember { mutableStateOf("PDF") }
    var templateSelecionado by remember { mutableStateOf<RelatorioTemplate?>(null) }
    var notificarInteressados by remember { mutableStateOf(false) }

    val templates = remember {
        listOf(
            RelatorioTemplate(
                "1",
                "Relatório Executivo",
                "Visão geral com principais KPIs e gráficos",
                TipoRelatorio.DESEMPENHO
            ),
            RelatorioTemplate(
                "2",
                "Análise Financeira Detalhada",
                "Métricas financeiras e projeções",
                TipoRelatorio.FINANCEIRO
            ),
            RelatorioTemplate(
                "3",
                "Dashboard Operacional",
                "Indicadores de desempenho operacional",
                TipoRelatorio.OPERACIONAL
            )
        )
    }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerar Novo Relatório",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Templates
            Text(
                "Templates Disponíveis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Lista de Templates
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                templates.forEach { template ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            templateSelecionado = template
                            tipoRelatorio = template.tipo
                        },
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (templateSelecionado?.id == template.id)
                                MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
                        )
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
                                    template.nome,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    template.descricao,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            RadioButton(
                                selected = templateSelecionado?.id == template.id,
                                onClick = {
                                    templateSelecionado = template
                                    tipoRelatorio = template.tipo
                                }
                            )
                        }
                    }
                }
            }

            Divider()

            // Configurações Básicas
            Text(
                "Configurações Básicas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Tipo de Relatório
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showTipoDialog = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Tipo de Relatório",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            tipoRelatorio.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }

            // Período
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showPeriodoDialog = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Período",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            periodoRelatorio.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }

            // Intervalo de Datas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = dateFormat.format(dataInicio),
                    onValueChange = { },
                    label = { Text("Data Início") },
                    modifier = Modifier.weight(1f),
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { /* Abrir DatePicker */ }) {
                            Icon(Icons.Default.CalendarToday, null)
                        }
                    }
                )
                OutlinedTextField(
                    value = dateFormat.format(dataFim),
                    onValueChange = { },
                    label = { Text("Data Fim") },
                    modifier = Modifier.weight(1f),
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { /* Abrir DatePicker */ }) {
                            Icon(Icons.Default.CalendarToday, null)
                        }
                    }
                )
            }

            Divider()

            // Conteúdo e Análise
            Text(
                "Conteúdo e Análise",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Indicadores-Chave (KPIs)")
                    Switch(
                        checked = incluirIndicadoresChave,
                        onCheckedChange = { incluirIndicadoresChave = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Gráficos e Visualizações")
                    Switch(
                        checked = incluirGraficos,
                        onCheckedChange = { incluirGraficos = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Comparativo Anual")
                    Switch(
                        checked = incluirComparativoAnual,
                        onCheckedChange = { incluirComparativoAnual = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Previsões e Tendências")
                    Switch(
                        checked = incluirPrevisoes,
                        onCheckedChange = { incluirPrevisoes = it }
                    )
                }
            }

            Divider()

            // Opções de Entrega
            Text(
                "Opções de Entrega",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Formato de Exportação
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { }
                ) {
                    OutlinedTextField(
                        value = formatoExportacao,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Formato de Exportação") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notificar Interessados")
                    Switch(
                        checked = notificarInteressados,
                        onCheckedChange = { notificarInteressados = it }
                    )
                }

                if (notificarInteressados) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("E-mails (separados por vírgula)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Preview */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Visibility, null)
                    Spacer(Modifier.width(8.dp))
                    Text("PREVIEW")
                }
                Button(
                    onClick = { /* Gerar */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) {
                    Icon(Icons.Default.Description, null)
                    Spacer(Modifier.width(8.dp))
                    Text("GERAR")
                }
            }
        }

        // Dialog de Tipo de Relatório
        if (showTipoDialog) {
            AlertDialog(
                onDismissRequest = { showTipoDialog = false },
                title = { Text("Selecionar Tipo") },
                text = {
                    Column {
                        TipoRelatorio.values().forEach { tipo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = tipoRelatorio == tipo,
                                    onClick = {
                                        tipoRelatorio = tipo
                                        showTipoDialog = false
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(tipo.name)
                            }
                        }
                    }
                },
                confirmButton = { }
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
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = periodoRelatorio == periodo,
                                    onClick = {
                                        periodoRelatorio = periodo
                                        showPeriodoDialog = false
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(periodo.name)
                            }
                        }
                    }
                },
                confirmButton = { }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun GerarRelatorioScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GerarRelatorioScreen(
                navController = rememberNavController()
            )
        }
    }
}
