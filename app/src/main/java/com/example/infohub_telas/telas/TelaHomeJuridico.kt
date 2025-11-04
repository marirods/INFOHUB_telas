package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.components.juridico.JuridicoMenuCard
import com.example.infohub_telas.components.juridico.JuridicoSectionTitle
import com.example.infohub_telas.navigation.JuridicoRoutes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHomeJuridico(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        Triple(Icons.Default.Business, "Gerenciar Empresas", JuridicoRoutes.GERENCIAMENTO_EMPRESAS),
        Triple(Icons.Default.Assessment, "Relatórios", JuridicoRoutes.RELATORIOS),
        Triple(Icons.Default.Inventory, "Produtos", JuridicoRoutes.PRODUTOS),
        Triple(Icons.Default.Add, "Nova Empresa", JuridicoRoutes.CADASTRO_EMPRESA),
        Triple(Icons.Default.Settings, "Configurações", JuridicoRoutes.CONFIGURACOES)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Portal Jurídico",
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Welcome Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Bem-vindo ao Portal Jurídico",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Gerencie suas empresas e documentos em um só lugar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            JuridicoSectionTitle(
                text = "Menu Principal",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Menu Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    menuItems.take(2).forEach { (icon, title, route) ->
                        JuridicoMenuCard(
                            icon = icon,
                            title = title,
                            backgroundColor = when (title) {
                                "Gerenciar Empresas" -> Color(0xFF2196F3)
                                "Relatórios" -> Color(0xFF4CAF50)
                                else -> MaterialTheme.colorScheme.primary
                            },
                            onClick = { navController.navigate(route) }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    menuItems.drop(2).forEach { (icon, title, route) ->
                        JuridicoMenuCard(
                            icon = icon,
                            title = title,
                            backgroundColor = when (title) {
                                "Nova Empresa" -> Color(0xFFF9A825)
                                "Configurações" -> Color(0xFF607D8B)
                                else -> MaterialTheme.colorScheme.primary
                            },
                            onClick = { navController.navigate(route) }
                        )
                    }
                }
            }

            JuridicoSectionTitle(
                text = "Atividades Recentes",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Recent Activities
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    listOf(
                        Triple("Nova empresa cadastrada", "Empresa ABC Ltda", "2h atrás"),
                        Triple("Relatório gerado", "Relatório Mensal", "3h atrás"),
                        Triple("Documento atualizado", "Contrato XYZ", "5h atrás")
                    ).forEach { (title, desc, time) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = time,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (time != "5h atrás") {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun TelaHomeJuridicoPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TelaHomeJuridico(
                navController = rememberNavController()
            )
        }
    }
}
