package com.example.infohub_telas.telas.juridico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.CompanyStatus
import com.example.infohub_telas.navigation.JuridicoRoutes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.StatusActive
import com.example.infohub_telas.ui.theme.StatusInactive
import com.example.infohub_telas.ui.theme.StatusPending

data class JuridicoEmpresa(
    val id: String,
    val nome: String,
    val cnpj: String,
    val status: CompanyStatus,
    val dataAbertura: String,
    val responsavel: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoGerenciamentoEmpresasScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }

    val empresas = remember {
        listOf(
            JuridicoEmpresa(
                "1",
                "ABC Comércio Ltda",
                "12.345.678/0001-90",
                CompanyStatus.ACTIVE,
                "01/01/2025",
                "João Silva"
            ),
            JuridicoEmpresa(
                "2",
                "XYZ Serviços ME",
                "98.765.432/0001-10",
                CompanyStatus.PENDING,
                "15/03/2025",
                "Maria Santos"
            ),
            JuridicoEmpresa(
                "3",
                "Tech Solutions S.A.",
                "45.678.901/0001-23",
                CompanyStatus.INACTIVE,
                "10/06/2025",
                "Pedro Oliveira"
            )
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerenciamento Jurídico",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filtrar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(JuridicoRoutes.CADASTRO_EMPRESA) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Adicionar Empresa")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { },
                active = false,
                onActiveChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar empresas...") },
                leadingIcon = { Icon(Icons.Default.Search, "Buscar") }
            ) { }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    empresas.filter {
                        it.nome.contains(searchQuery, ignoreCase = true) ||
                        it.cnpj.contains(searchQuery)
                    }
                ) { empresa ->
                    JuridicoEmpresaCard(
                        empresa = empresa,
                        onEditClick = { navController.navigate(JuridicoRoutes.CADASTRO_EMPRESA + "/${empresa.id}") },
                        onRelatorioClick = { navController.navigate(JuridicoRoutes.RELATORIOS + "/${empresa.id}") }
                    )
                }
            }
        }

        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtrar Empresas") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Status")
                        Column {
                            CompanyStatus.values().forEach { status ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Checkbox(
                                        checked = true,
                                        onCheckedChange = { }
                                    )
                                    Text(
                                        text = when (status) {
                                            CompanyStatus.ACTIVE -> "Ativa"
                                            CompanyStatus.PENDING -> "Pendente"
                                            CompanyStatus.INACTIVE -> "Inativa"
                                        },
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JuridicoEmpresaCard(
    empresa: JuridicoEmpresa,
    onEditClick: () -> Unit,
    onRelatorioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = empresa.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = empresa.cnpj,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = when (empresa.status) {
                        CompanyStatus.ACTIVE -> StatusActive
                        CompanyStatus.INACTIVE -> StatusInactive
                        CompanyStatus.PENDING -> StatusPending
                    }.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = when (empresa.status) {
                            CompanyStatus.ACTIVE -> "Ativa"
                            CompanyStatus.INACTIVE -> "Inativa"
                            CompanyStatus.PENDING -> "Pendente"
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = when (empresa.status) {
                            CompanyStatus.ACTIVE -> StatusActive
                            CompanyStatus.INACTIVE -> StatusInactive
                            CompanyStatus.PENDING -> StatusPending
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailItem(
                    label = "Data Abertura",
                    value = empresa.dataAbertura
                )
                DetailItem(
                    label = "Responsável",
                    value = empresa.responsavel
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onRelatorioClick) {
                    Icon(Icons.Default.Assessment, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Relatórios")
                }
                TextButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Editar")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun JuridicoGerenciamentoEmpresasScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            JuridicoGerenciamentoEmpresasScreen(
                navController = rememberNavController()
            )
        }
    }
}
