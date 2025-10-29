package com.example.infohub_telas.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.model.Company
import com.example.infohub_telas.model.CompanyMockData
import com.example.infohub_telas.model.CompanyStatus
import com.example.infohub_telas.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerenciamentoEmpresasScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var companies by remember { mutableStateOf(CompanyMockData.sampleCompanies) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gerenciamento de Empresas") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, "Filtrar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("cadastroEmpresa") }
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
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* Implement search */ },
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar empresas...") },
                leadingIcon = { Icon(Icons.Default.Search, "Buscar") }
            ) {}

            // Companies List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(companies.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }) { company ->
                    CompanyCard(
                        company = company,
                        onEditClick = { navController.navigate("cadastroEmpresa/${company.id}") },
                        onDeleteClick = { /* Implement delete */ },
                        onReportClick = { navController.navigate("relatoriosEmpresariais/${company.id}") }
                    )
                }
            }
        }
    }

    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filtrar Empresas") },
            text = {
                Column {
                    // Add filter options here
                    Text("Implementar filtros...")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyCard(
    company: Company,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = company.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = company.cnpj,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                StatusChip(status = company.status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Excluir",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                IconButton(onClick = onReportClick) {
                    Icon(
                        Icons.Default.Assessment,
                        contentDescription = "Ver Relatório",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(
    status: CompanyStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, text) = when (status) {
        CompanyStatus.ACTIVE -> StatusActive to "Ativa"
        CompanyStatus.INACTIVE -> StatusInactive to "Inativa"
        CompanyStatus.PENDING -> StatusPending to "Pendente"
    }

    Surface(
        modifier = modifier,
        color = backgroundColor.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = backgroundColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyCardPreview() {
    CompanyCard(
        company = CompanyMockData.sampleCompanies[0],
        onEditClick = {},
        onDeleteClick = {},
        onReportClick = {}
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun GerenciamentoEmpresasScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GerenciamentoEmpresasScreen(
                navController = rememberNavController()
            )
        }
    }
}
