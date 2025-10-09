package com.example.infohub_telas.telas


import com.example.infohub_telas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaLocalizacao(navController: NavController?) {

    val retrofitFactory = RetrofitFactory()
    val infoHubService = retrofitFactory.getInfoHub_UserService()
    val scope = rememberCoroutineScope()

    val viaCepService = retrofitFactory.getViaCepService()
    val brasilApiService = retrofitFactory.getBrasilApiService()

    val userApi = retrofitFactory.getInfoHub_UserService()

    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment
            .CenterHorizontally
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9A01B)) //
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        // MAPA
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.loc_menu), // você pode trocar por print do mapa
                contentDescription = "Mapa",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(20.dp)
        )

        // BARRA DE BUSCA
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(8.dp, RoundedCornerShape(16.dp)) // sombra
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = cep,
                onValueChange = { cep = it },
                placeholder = { Text("Digite o CEP") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                scope.launch {
                    try {
                        val resposta = viaCepService.buscarCep(cep)
                        endereco = "${resposta.logradouro}, ${resposta.bairro}, ${resposta.localidade} - ${resposta.uf}"
                    } catch (e: Exception) {
                        endereco = "CEP não encontrado"
                    }
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.lupa_loc),
                    contentDescription = "Buscar",
                    modifier = Modifier.size(24.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.microfone_loc),
                contentDescription = "Microfone",
                modifier = Modifier.size(24.dp)
            )
        }


            if (endereco != null) {
                Text(
                    text = endereco ?: "",
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(250.dp))

        // BOTÃO "Ver Carrinho"
        Button(
            onClick = { },
            colors = ButtonDefaults
                .buttonColors
                    (containerColor = Color(0xFFF9A01B)),
            shape = RoundedCornerShape(
                12.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
        ) {
            Text(
                "Ver carrinho   R$ 0,00",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier
                .height(13.dp)
        )

        // MENU INFERIOR
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .width(90.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment
                    .CenterHorizontally)
            {
                Image(
                    painter = painterResource(
                        id = R.drawable.loja_menu),
                    contentDescription = "Início",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    "Início",
                    fontSize = 12.sp)
            }
            Column(
                horizontalAlignment = Alignment
                    .CenterHorizontally)
            {
                Image(
                    painter = painterResource(
                        id = R.drawable.etiqueta_menu),
                    contentDescription = "Promoções",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    "Promoções",
                    fontSize = 12.sp
                )
            }
            Column(
                horizontalAlignment = Alignment
                    .CenterHorizontally)
            {
                Box(
                    modifier = Modifier
                        .size(67.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painter = painterResource
                            (id = R.drawable.loc_menu),
                        contentDescription = "Localização",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Localização",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF9A01B),


                        )
                }
            }
            Column(
                horizontalAlignment = Alignment
                    .CenterHorizontally
            )
            {
                Image(
                    painter = painterResource(
                        id = R.drawable.cash_menu),
                    contentDescription = "InfoCash",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    "InfoCash",
                    fontSize = 12.sp
                )
            }
            Column(
                horizontalAlignment = Alignment
                    .CenterHorizontally
            )
            {
                Image(
                    painter = painterResource(
                        id = R.drawable.perfil_icon),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Meu Perfil",
                    fontSize = 12.sp)
            }
        }
    }

}




@Preview(showSystemUi = true)
@Composable
fun TelaLocalizacaoPreview() {
    InfoHub_telasTheme {
        TelaLocalizacao(null)}}