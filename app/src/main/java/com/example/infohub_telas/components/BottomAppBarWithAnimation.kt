package com.example.infohub_telas.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes

private val OrangeColor = Color(0xFFF9A01B)
private val DarkOrangeColor = Color(0xFFF9A01B)
private val TextGrayColor = Color(0xFF888888)

private data class NavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

private val navItems = listOf(
    NavItem("Início", Routes.HOME, Icons.Default.Home),
    NavItem("Promoções", Routes.LISTA_PRODUTOS, Icons.Default.LocalOffer),
    NavItem("Localização", Routes.LOCALIZACAO, Icons.Default.LocationOn),
    NavItem("InfoCash", Routes.CHAT_PRECOS, Icons.Filled.AttachMoney),
    NavItem("Meu Perfil", Routes.PERFIL, Icons.Default.Person)
)

@Composable
fun BottomAppBarWithAnimation(
    navController: NavController,
    currentRoute: String,
    onTabSelected: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / navItems.size
    val selectedIndex = navItems.indexOfFirst { it.route == currentRoute }

    val indicatorOffset by animateDpAsState(
        targetValue = itemWidth * selectedIndex,
        label = "indicatorOffset"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Button(
            onClick = { navController.navigate(Routes.CARRINHO) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkOrangeColor),
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
                    modifier = Modifier
                        .size(30.dp)
                )
                Text("Ver carrinho", color = Color.White, fontWeight = FontWeight.Bold)
                Text("R$ 0,00", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = selectedIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            onTabSelected(item.route)
                            navController.navigate(item.route)
                        },
                        icon = {
                            Box(contentAlignment = Alignment.Center) {
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .offset(x = indicatorOffset - (itemWidth * index))
                                            .background(OrangeColor)
                                    )
                                }
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = if (isSelected) Color.White else TextGrayColor,
                                    modifier = Modifier.zIndex(1f)
                                )
                            }
                        },
                        label = {
                            Text(
                                item.label,
                                fontSize = 10.sp,
                                color = if (isSelected) OrangeColor else TextGrayColor
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "BottomAppBarWithAnimation")
@Composable
fun BottomAppBarWithAnimationPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        BottomAppBarWithAnimation(
            navController = navController,
            currentRoute = "profile",
            onTabSelected = {}
        )
    }
}
