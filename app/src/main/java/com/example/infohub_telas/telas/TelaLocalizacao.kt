package com.example.infohub_telas.telas

import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLocalizacao(navController: NavController) {

    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf<String?>(null) }
    var feedbackMessage by remember { mutableStateOf<Pair<String, Color>?>(null) }


    val mapView = if (!isPreview) {
        remember {
            org.osmdroid.views.MapView(context).apply {
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(-23.55052, -46.633308))

                val marker = Marker(this)
                marker.position = GeoPoint(-23.55052, -46.633308)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "Você está aqui"
                overlays.add(marker)
            }
        }
    } else null


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9A01B))
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
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            if (!isPreview) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(300.dp)
                ) {
                    AndroidView(factory = { mapView!! }, modifier = Modifier.fillMaxSize())
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(300.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Mapa (Preview)")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))



            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = cep,
                        onValueChange = {
                            cep = it
                            feedbackMessage = null // Limpa a mensagem ao digitar
                        },
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
                                    val viaCepService = RetrofitFactory().getViaCepService()
                                    val resposta = viaCepService.buscarCep(input)
                                    enderecoResult = "${resposta.logradouro}, ${resposta.bairro}, ${resposta.localidade} - ${resposta.uf}"
                                } else {
                                    enderecoResult = input
                                }

                                if (!isPreview) {
                                    val geo = Geocoder(context).getFromLocationName(enderecoResult, 1)
                                    if (!geo.isNullOrEmpty()) {
                                        val loc = geo[0]
                                        val lat = loc.latitude
                                        val lon = loc.longitude

                                        mapView!!.controller.setCenter(GeoPoint(lat, lon))

                                        val marker = Marker(mapView)
                                        marker.position = GeoPoint(lat, lon)
                                        marker.title = enderecoResult

                                        mapView.overlays.clear()
                                        mapView.overlays.add(marker)
                                        mapView.invalidate()
                                        endereco = enderecoResult
                                        feedbackMessage = "Endereço encontrado com sucesso!" to Color(0xFF006400) // Verde escuro
                                    } else {
                                        throw Exception("Endereço não localizado no mapa.")
                                    }
                                } else { // Lógica para preview
                                    endereco = enderecoResult
                                    feedbackMessage = "Endereço encontrado com sucesso!" to Color(0xFF006400)
                                }

                            } catch (e: Exception) {
                                endereco = null
                                feedbackMessage = "Endereço não encontrado. Tente novamente." to Color.Red
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


                feedbackMessage?.let { (message, color) ->
                    Text(
                        text = message,
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 14.sp,
                        color = color
                    )
                }


            }
        }

        // Menu inferior fixo na base
        BottomMenu(navController = navController)
    }
}




@Preview(showSystemUi = true)
@Composable
fun TelaLocalizacaoPreview() {
    InfoHub_telasTheme {
        TelaLocalizacao(rememberNavController())
    }
}
