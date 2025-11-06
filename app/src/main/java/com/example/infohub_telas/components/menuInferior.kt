package com.example.infohub_telas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import com.example.infohub_telas.navigation.JuridicoRoutes
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
fun BottomMenu(navController: NavController, isAdmin: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Botão de carrinho
        Button(
            onClick = { navController.navigate(Routes.CARRINHO) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B)),
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
                    modifier = Modifier.size(30.dp)
                )
                Text("Ver carrinho", color = Color.White, fontWeight = FontWeight.Bold)
                Text("R$ 0,00", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        
        // Menu de navegação
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
        
        // Pessoa Jurídica (Admin) vê botão Jurídico
        if (isAdmin) {
            MenuItem(
                icon = Icons.Default.Gavel,
                label = "Jurídico",
                navController = navController,
                route = JuridicoRoutes.HOME
            )
        } else {
            // Pessoa Física vê botão Meu Perfil
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

// Preview usando NavController fake para renderização
@Preview(showBackground = true)
@Composable
fun BottomMenuPreview() {
    val fakeNavController = rememberNavController() // só para preview, não navega
    BottomMenu(navController = fakeNavController)
}
