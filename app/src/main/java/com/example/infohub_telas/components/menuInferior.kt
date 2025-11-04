package com.example.infohub_telas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person

// Cor principal do app
val Laranja = Color(0xFFF9A01B)
val CinzaTexto = Color(0xFF888888)

// Item do menu inferior
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
                    launchSingleTop = true
                    restoreState = true
                }
            } catch (e: Exception) {
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
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

// Menu inferior com botão do carrinho
@Composable
fun BottomMenuWithCart(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // 🔸 Botão "Ver carrinho"
        Button(
            onClick = { navController.navigate(Routes.CARRINHO) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Laranja),
            shape = MaterialTheme.shapes.large
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sacola),
                    contentDescription = "Sacola",
                    modifier = Modifier.size(28.dp)
                )
                Text("Ver carrinho", color = Color.White, fontWeight = FontWeight.Bold)
                Text("R$ 0,00", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // 🔸 Menu inferior
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
                label = "Produtos",
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
                label = "Chat",
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
}

// Preview
@Preview(showBackground = true)
@Composable
fun BottomMenuWithCartPreview() {
    val fakeNavController = rememberNavController()
    BottomMenuWithCart(navController = fakeNavController)
}
