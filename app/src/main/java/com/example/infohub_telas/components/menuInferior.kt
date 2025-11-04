package com.example.infohub_telas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes

// Item do menu com navegação
@Composable
fun MenuItem(
    iconRes: Int,
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
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
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
            iconRes = R.drawable.loja_menu,
            label = "Início",
            navController = navController,
            route = Routes.HOME
        )
        MenuItem(
            iconRes = R.drawable.etiqueta_menu,
            label = "Produtos",
            navController = navController,
            route = Routes.LISTA_PRODUTOS
        )
        MenuItem(
            iconRes = R.drawable.local,
            label = "Localização",
            navController = navController,
            route = Routes.LOCALIZACAO
        )
        MenuItem(
            iconRes = R.drawable.cash_menu,
            label = "Chat",
            navController = navController,
            route = Routes.CHAT_PRECOS
        )
        MenuItem(
            iconRes = R.drawable.perfil_icon,
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
