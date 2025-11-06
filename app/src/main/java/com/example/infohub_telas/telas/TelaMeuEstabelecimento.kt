package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.components.InfoRow
import com.example.infohub_telas.components.MyTopAppBar
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMeuEstabelecimento(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Home Jurídico") },
                    selected = false,
                    onClick = { navController.navigate("homeJuridico") }
                )
                NavigationDrawerItem(
                    label = { Text("Meu Estabelecimento") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Lista de Produtos") },
                    selected = false,
                    onClick = { navController.navigate("listaProdutos") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    title = "Meu Estabelecimento",
                    navigationIcon = Icons.Default.Menu,
                    onNavigationIconClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = { BottomMenu(navController = navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        InfoRow(icon = Icons.Default.Business, label = "Nome", text = "Estabelecimento Exemplo")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        InfoRow(icon = Icons.Default.Receipt, label = "CNPJ", text = "12.345.678/0001-99")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        InfoRow(icon = Icons.Default.LocationOn, label = "Endereço", text = "Rua Exemplo, 123")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        InfoRow(icon = Icons.Default.Phone, label = "Telefone", text = "(11) 99999-8888")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        InfoRow(icon = Icons.Default.Email, label = "E-mail", text = "contato@exemplo.com")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        InfoRow(icon = Icons.Default.Category, label = "Categoria", text = "Alimentação")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("cadastroEstabelecimento?id=1&categoria=Alimentação")
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar Dados")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Editar Dados", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { navController.navigate("promocoes") },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ver Promoções", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = { navController.navigate("criarPromocao") },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green color for "create"
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Criar Promoção")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Criar Promoção", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TelaMeuEstabelecimentoPreview() {
    InfoHub_telasTheme {
        TelaMeuEstabelecimento(navController = rememberNavController())
    }
}
