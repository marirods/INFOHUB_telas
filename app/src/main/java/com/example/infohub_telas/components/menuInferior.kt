package com.example.infohub_telas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.PrimaryOrange

// Item do menu com navegação
@Composable
fun MenuItem(
    icon: ImageVector,
    label: String,
    navController: NavController?,
    route: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { 
            try {
                navController?.navigate(route) {
                    // Evita múltiplas instâncias da mesma tela
                    launchSingleTop = true
                    // Restaura o estado se a tela já existir na pilha
                    restoreState = true
                }
            } catch (e: Exception) {
                // Se a rota não existir, não faz nada
                e.printStackTrace()
            }
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = PrimaryOrange
        )
        Text(
            text = label,
            fontSize = 12.sp
        )
    }
}

// Menu inferior completo
@Composable
fun BottomMenu(
    navController: NavController?,
    isAdmin: Boolean = false,
    userPerfil: String? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botão de carrinho
            Button(
                onClick = { navController?.navigate(Routes.CARRINHO) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sacola),
                        contentDescription = "Sacola",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Ver carrinho",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "R$ 0,00",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Menu de navegação
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primeiro item - Sempre HOME
                MenuItem(
                    icon = Icons.Default.Home,
                    label = "Início",
                    navController = navController,
                    route = Routes.HOME
                )

                // Menu específico baseado no perfil
                if (userPerfil?.lowercase() == "juridico" ||
                    userPerfil?.lowercase() == "jurídico") {
                    // Menu para JURÍDICO
                    MenuItem(
                        icon = Icons.Default.Business,
                        label = "Empresas",
                        navController = navController,
                        route = Routes.GERENCIAMENTO_EMPRESAS
                    )
                    MenuItem(
                        icon = Icons.Default.LocalOffer,
                        label = "Dashboard",
                        navController = navController,
                        route = Routes.DASHBOARD_EMPRESA
                    )
                    MenuItem(
                        icon = Icons.Default.ShoppingBag,
                        label = "Relatórios",
                        navController = navController,
                        route = Routes.RELATORIOS
                    )
                    MenuItem(
                        icon = Icons.Default.Person,
                        label = "Perfil",
                        navController = navController,
                        route = Routes.PERFIL
                    )
                } else if (userPerfil?.lowercase() == "estabelecimento") {
                    // Menu para ESTABELECIMENTO
                    MenuItem(
                        icon = Icons.Default.LocalOffer,
                        label = "Promoções",
                        navController = navController,
                        route = Routes.LISTA_PRODUTOS
                    )
                    MenuItem(
                        icon = Icons.Default.ShoppingBag,
                        label = "Produtos",
                        navController = navController,
                        route = Routes.LISTA_PRODUTOS
                    )
                    MenuItem(
                        icon = Icons.Default.Business,
                        label = "Meu Negócio",
                        navController = navController,
                        route = Routes.MEU_ESTABELECIMENTO
                    )
                    MenuItem(
                        icon = Icons.Default.Person,
                        label = "Perfil",
                        navController = navController,
                        route = Routes.PERFIL
                    )
                } else {
                    // Menu para CONSUMIDOR (padrão)
                    MenuItem(
                        icon = Icons.Default.LocalOffer,
                        label = "Promoções",
                        navController = navController,
                        route = Routes.LISTA_PRODUTOS
                    )
                    MenuItem(
                        icon = Icons.Default.LocationOn,
                        label = "Localização",
                        navController = navController,
                        route = Routes.LOCALIZACAO
                    )
                    MenuItem(
                        icon = Icons.Filled.AttachMoney,
                        label = "InfoCash",
                        navController = navController,
                        route = Routes.INFOCASH
                    )
                    MenuItem(
                        icon = Icons.Default.Person,
                        label = "Meu Perfil",
                        navController = navController,
                        route = Routes.PERFIL
                    )
                }
            }
        }
    }
}


