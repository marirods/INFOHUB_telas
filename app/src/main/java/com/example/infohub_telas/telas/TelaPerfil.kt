package com.example.infohub_telas.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomAppBarWithAnimation

// Define the main colors from the image for easy reuse
private val OrangeColor = Color(0xFFF9A01B)
private val DarkOrangeColor = Color(0xFFF9A01B)
private val LightGrayColor = Color(0xFFF7F7F7)
private val TextGrayColor = Color(0xFF888888)
private val GreenColor = Color(0xFF25992E)
private val RedColor = Color(0xFFDA312A)

/**
 * The main entry point for the Profile Screen.
 * It uses a Scaffold to structure the layout with a top bar, bottom bar, and scrollable content.
 */
@Composable
fun TelaPerfil(navController: NavController?) {
    var currentRoute by remember {
        mutableStateOf("profile")
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomAppBarWithAnimation(
                navController = navController ?: return@Scaffold, // evita null
                currentRoute = currentRoute,
                onTabSelected = { newRoute -> currentRoute = newRoute }
            )
        },
        containerColor = LightGrayColor
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { ProfileHeader() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { HubCoinCard() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { FavoriteMarketsCard() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SettingsCard() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp) // 🔸 tamanho total do círculo
                    .background(
                        Color.White,
                        shape = CircleShape
                    ), // 🔸 círculo branco atrás do logo
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OrangeColor
        )
    )
}

// All the content cards remain the same as before.
@Composable
fun ProfileHeader() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment
                .CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(8.dp)
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(OrangeColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "iJ",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons
                            .Default.Edit,
                        contentDescription = "Editar Perfil"
                    )
                }
            }
            Text(
                "ISRAEL JUNIOR",
                fontWeight = FontWeight
                    .Bold, fontSize = 20.sp
            )
            Text(
                "raraeldev@gmail.com",
                color = TextGrayColor,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(OrangeColor.copy(alpha = 0.8f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Localização",
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(
                    Modifier.width(4.dp)
                )
                Text(
                    "São Paulo, SP",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
            Spacer(
                Modifier.height(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "R$ 1250.80",
                    label = "Total economizado",
                    valueColor = GreenColor)
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "45",
                    label = "Promoções usadas",
                    valueColor = OrangeColor
                    )
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "12",
                    label = "Avaliações feitas",
                    valueColor = RedColor
                    )
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "#12",
                    label = "Ranking geral",
                    valueColor = GreenColor
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    valueColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEAEAEA)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp) // Espaço fixo entre os textos
        ) {
            Text(
                value,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = valueColor,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp // Controla a altura da linha
            )

            Text(
                label,
                color = TextGrayColor,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp // Controla a altura da linha
            )
        }
    }
}

@Composable
fun HubCoinCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults
            .cardColors(containerColor = Color.White),
        elevation = CardDefaults
            .cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment
                    .CenterVertically
            ) {
                Icon(
                    Icons
                        .Default.MonetizationOn,
                    contentDescription = "HubCoin",
                    tint = DarkOrangeColor
                )
                Spacer(
                    Modifier
                        .width(8.dp)
                )
                Text(
                    "HubCoin",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(
                Modifier
                    .height(8.dp)
            )
            Text(
                "1.285HC",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DarkOrangeColor
            )
            Spacer(
                Modifier
                    .height(8.dp)
            )
            LinearProgressIndicator(
                progress = { 0.8f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = GreenColor,
                trackColor = OrangeColor.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun FavoriteMarketsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment
                    .CenterVertically
            ) {
                Icon(
                    Icons
                        .Default
                        .Favorite,
                    contentDescription = "Favoritos",
                    tint = RedColor
                )
                Spacer(
                    Modifier
                        .width(8.dp)
                )
                Text(
                    "Mercados Favoritos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(
                Modifier
                    .height(16.dp)
            )
            MarketItem(
                "Supermercado Japão", "8 visitas"
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            MarketItem(
                "Supermercado Japão", "5 visitas"
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MarketItem(
                "Supermercado Japão", "3 visitas"
            )
        }
    }
}

@Composable
fun MarketItem(
    name: String,
    visits: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment
            .CenterVertically,
        horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment
                .CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(LightGrayColor),
                contentAlignment = Alignment
                    .Center
            ) {
                Text(
                    "JP",
                    fontSize = 12.sp,
                    fontWeight = FontWeight
                        .Bold,
                    color = TextGrayColor
                )
            }
            Spacer(
                Modifier
                    .width(12.dp)
            )
            Column {
                Text(
                    name,
                    fontWeight = FontWeight
                        .SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    "500m",
                    color = TextGrayColor,
                    fontSize = 12.sp
                )
            }
        }
        Column(
            horizontalAlignment = Alignment
                .End
        )
        {
            Text(
                visits
                    .split(" ")[0],
                fontWeight = FontWeight
                    .Bold,
                fontSize = 16.sp
            )

            Text(
                "visitas",
                color = TextGrayColor,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun SettingsCard() {
    var pushNotifications
            by remember { mutableStateOf(true) }
    var location by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults
            .cardColors(
                containerColor = Color.White
            ),
        elevation = CardDefaults
            .cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment
                    .CenterVertically
            ) {
                Icon(
                    Icons
                        .Default.Settings,
                    contentDescription = "Configurações"
                )
                Spacer(
                    Modifier.width(8.dp)
                )
                Text(
                    "Configurações",
                    fontWeight = FontWeight
                        .Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(16.dp))
            SettingToggleItem(
                "Notificações push",
                "Receba alertas de promoções",
                pushNotifications
            ) {
                pushNotifications = it
            }
            SettingToggleItem("Localização", "Para mostrar ofertas próximas", location) {
                location = it
            }
            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
            SettingsButton(text = "Gerenciar Notificações", icon = Icons.Default.Notifications)
            Spacer(Modifier.height(8.dp))
            SettingsButton(text = "Alterar localização", icon = Icons.Default.LocationOn)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { /* Sair da conta */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Sair da Conta")
            }
        }
    }
}

@Composable
fun SettingToggleItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = TextGrayColor, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = GreenColor
            )
        )
    }
}

@Composable
fun SettingsButton(text: String, icon: ImageVector) {
    OutlinedButton(
        onClick = { /* Handle click */ },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(icon, contentDescription = null, tint = Color.Gray)
            Spacer(Modifier.width(16.dp))
            Text(text, color = Color.Gray, fontWeight = FontWeight.Normal)
        }
    }
}

// =================================================================================
// PREVIEWS
// =================================================================================

@Preview(showBackground = true, name = "Tela Perfil Completa")
@Composable
fun TelaPerfilScreenPreview() {
    MaterialTheme {
        TelaPerfil(navController = null)
    }
}

@Preview(showBackground = true, name = "HubCoin Card")
@Composable
fun HubCoinCardPreview() {
    MaterialTheme {
        HubCoinCard()
    }
}

@Preview(showBackground = true, name = "Favorite Markets Card")
@Composable
fun FavoriteMarketsCardPreview() {
    MaterialTheme {
        // This component contains MarketItem, which is fine for preview.
        FavoriteMarketsCard()
    }
}

@Preview(showBackground = true, name = "Market Item")
@Composable
fun MarketItemPreview() {
    MaterialTheme {
        // Provide sample data for the preview
        MarketItem(name = "Supermercado Exemplo", visits = "15 visitas")
    }
}

@Preview(showBackground = true, name = "Settings Card")
@Composable
fun SettingsCardPreview() {
    MaterialTheme {
        SettingsCard()
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
