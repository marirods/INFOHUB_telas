package com.example.infohub_telas.telas


import android.location.Geocoder
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import com.example.infohub_telas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.launch
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    if (permissionState.status.isGranted) {
      TelaLocalizacao(null)
    } else {
        Text("A permissão de localização é necessária")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaLocalizacao(navController: NavController?) {

    val context = LocalContext.current
    val retrofitFactory = RetrofitFactory()
    val infoHubService = retrofitFactory.getInfoHub_UserService()
    val scope = rememberCoroutineScope()

    val viaCepService = retrofitFactory.getViaCepService()
    val brasilApiService = retrofitFactory.getBrasilApiService()

    val userApi = retrofitFactory.getInfoHub_UserService()

    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf<String?>(null) }

    var ativo: Boolean = true

    val mapView = remember {
        org.osmdroid.views.MapView(context).apply {
            setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15.0)
            controller.setCenter(GeoPoint(-23.55052, -46.633308)) // São Paulo inicial

            // Marcador inicial
            val marker = Marker(this)
            marker.position = GeoPoint(-23.55052, -46.633308)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = "Você está aqui"
            overlays.add(marker)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment
            .CenterHorizontally
    ) {


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


        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(300.dp)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )
        }


        Spacer(modifier = Modifier.height(20.dp)
        )


        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(9.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = cep,
                onValueChange = { cep = it },
                placeholder = { Text("Digite um endereço ou CEP (ex: 01310-100...)") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                scope.launch {
                    try {
                        val input = cep.trim()
                        var enderecoResult: String? = null

                        if (input.matches(Regex("\\d{5}-?\\d{3}"))) {
                            val resposta = viaCepService.buscarCep(input)
                            enderecoResult = "${resposta.logradouro}, ${resposta.bairro}, ${resposta.localidade} - ${resposta.uf}"
                        } else {
                            // Se não for CEP, assume que é endereço digitado
                            enderecoResult = input
                        }

                        endereco = enderecoResult


                        val geo = Geocoder(context).getFromLocationName(endereco!!, 1)
                        if (!geo.isNullOrEmpty()) {
                            val loc = geo[0]
                            val lat = loc.latitude
                            val lon = loc.longitude

                            mapView.controller.setCenter(GeoPoint(lat, lon))

                            val marker = Marker(mapView)
                            marker.position = GeoPoint(lat, lon)
                            marker.title = enderecoResult

                            mapView.overlays.clear()
                            mapView.overlays.add(marker)
                            mapView.invalidate()
                        }
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

//        // BOTÃO "Ver Carrinho"
//        Button(
//            onClick = { },
//            colors = ButtonDefaults
//                .buttonColors
//                    (containerColor = Color(0xFFF9A01B)),
//            shape = RoundedCornerShape(
//                12.dp),
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .height(50.dp)
//        ) {
//            Text(
//                "Ver carrinho   R$ 0,00",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        Spacer(
//            modifier = Modifier
//                .height(13.dp)
//        )

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
                val animScale by animateFloatAsState(
                    targetValue = if (ativo) 1.1f else 1f,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            if (ativo) Color(0xFFF9A01B) else Color.White,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center

                ) {
                    Column(
                        horizontalAlignment = Alignment
                            .CenterHorizontally)
                    {

                        Image(
                            painter = painterResource
                                (id = R.drawable.loc_menu),
                            contentDescription = "Localização",
                            modifier = Modifier.size(24.dp),

                        )
                        Text(
                            "Localização",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (ativo) Color.White else Color.Black
                        )

                    }
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