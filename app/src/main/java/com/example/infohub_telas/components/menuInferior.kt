package com.example.infohub_telas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.telas.Laranja

// Item do menu com navegação
@Composable
fun MenuItem(
    icon: ImageVector,
    label: String,
    navController: NavController,
    route: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { 
            try {
                navController.navigate(route) {
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
            tint = Laranja
        )
        Text(
            text = label,
            fontSize = 12.sp
        )
    }
}

// Menu inferior completo
@Composable
fun BottomMenu(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(
            icon = Icons.Default.Home,
            label = "Início",
            navController = navController,
            route = Routes.HOME
        )
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
            route = Routes.CHAT_PRECOS
        )
        MenuItem(
            icon = Icons.Default.Person,
            label = "Meu Perfil",
            navController = navController,
            route = Routes.PERFIL
        )
    }
}

// Preview usando NavController fake para renderização
@Preview(showBackground = true)
@Composable
fun BottomMenuPreview() {
    val fakeNavController = rememberNavController() // só para preview, não navega
    BottomMenu(navController = fakeNavController)
}
