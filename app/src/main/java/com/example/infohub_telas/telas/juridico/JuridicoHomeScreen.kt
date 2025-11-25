package com.example.infohub_telas.telas.juridico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.navigation.JuridicoRoutes
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

data class JuridicoMenuItem(
    val icon: ImageVector,
    val title: String,
    val route: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoHomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        JuridicoMenuItem(
            icon = Icons.Default.Business,
            title = "Gerenciar Empresas",
            route = JuridicoRoutes.GERENCIAMENTO_EMPRESAS,
            color = Color(0xFF2196F3)
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Add,
            title = "Novo Estabelecimento",
            route = Routes.CADASTRO_ESTABELECIMENTO,
            color = Color(0xFF4CAF50)
        ),
        JuridicoMenuItem(
            icon = Icons.Default.ShoppingBag,
            title = "Novo Produto",
            route = Routes.CADASTRO_PRODUTO,
            color = Color(0xFFF9A825)
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Assessment,
            title = "Relatórios",
            route = "${Routes.DETALHES_RELATORIO.replace("{relatorioId}", "1")}", // ID padrão
            color = Color(0xFF9C27B0)
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Store,
            title = "Meus Estabelecimentos",
            route = Routes.LISTA_PRODUTOS, // Reutilizar para mostrar produtos por estabelecimento
            color = Color(0xFF607D8B)
        ),
        JuridicoMenuItem(
            icon = Icons.Default.AccountBox,
            title = "Perfil",
            route = Routes.PERFIL,
            color = Color(0xFF795548)
        )
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Portal Jurídico",
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
            item {
                WelcomeCard()
            }

            item {
                Text(
                    "Menu Principal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.height(350.dp)
                ) {
                    items(menuItems) { item ->
                        MenuCard(
                            menuItem = item,
                            onClick = { navController.navigate(item.route) }
                        )
                    }
                }
            }

            item {
                Text(
                    "Atividades Recentes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {
                RecentActivitiesCard()
            }
        }
    }
}

@Composable
private fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PrimaryOrange)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Bem-vindo ao Portal Jurídico",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Gerencie empresas, relatórios e processos em um só lugar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuCard(
    menuItem: JuridicoMenuItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = menuItem.color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = menuItem.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.titleMedium,
                color = menuItem.color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun RecentActivitiesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecentActivityItem(
                icon = Icons.Default.Add,
                title = "Nova empresa cadastrada",
                description = "Empresa ABC Ltda",
                time = "Há 2 horas"
            )
            Divider()
            RecentActivityItem(
                icon = Icons.Default.Assessment,
                title = "Relatório gerado",
                description = "Relatório mensal de desempenho",
                time = "Há 3 horas"
            )
            Divider()
            RecentActivityItem(
                icon = Icons.Default.Edit,
                title = "Dados atualizados",
                description = "Perfil da empresa XYZ atualizado",
                time = "Há 5 horas"
            )
        }
    }
}

@Composable
private fun RecentActivityItem(
    icon: ImageVector,
    title: String,
    description: String,
    time: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
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
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun JuridicoHomeScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            JuridicoHomeScreen(
                navController = rememberNavController()
            )
        }
    }
}
