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
        modifier = Modifier.clickable { navController.navigate(route) }
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
            route = "inicio"
        )
        MenuItem(
            iconRes = R.drawable.etiqueta_menu,
            label = "Promoções",
            navController = navController,
            route = "promocoes"
        )
        MenuItem(
            iconRes = R.drawable.local,
            label = "Localização",
            navController = navController,
            route = "localizacao"
        )
        MenuItem(
            iconRes = R.drawable.cash_menu,
            label = "InfoCash",
            navController = navController,
            route = "infocash"
        )
        MenuItem(
            iconRes = R.drawable.perfil_icon,
            label = "Meu Perfil",
            navController = navController,
            route = "perfil"
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
