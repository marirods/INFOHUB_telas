package com.example.infohub_telas.telas

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

suspend fun buscarMercadosProximos(lat: Double, lon: Double): List<Pair<String, GeoPoint>> = withContext(Dispatchers.IO) {
    val mercados = mutableListOf<Pair<String, GeoPoint>>()
    var conn: HttpURLConnection? = null
    try {
        val query = URLEncoder.encode("supermercado", "UTF-8")
        val urlStr =
            "https://nominatim.openstreetmap.org/search?q=$query&format=json&limit=10&" +
                    "viewbox=${lon - 0.1},${lat + 0.1},${lon + 0.1},${lat - 0.1}&bounded=1"

        Log.d("NOMINATIM_DEBUG", "Buscando mercados próximos: $urlStr")

        val url = URL(urlStr)
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("User-Agent", "InfoHubApp/1.0 (student@infohub.com)")
        conn.connectTimeout = 15000
        conn.readTimeout = 15000
        conn.connect()

        if (conn.responseCode == HttpURLConnection.HTTP_OK) {
            conn.inputStream.bufferedReader().use {
                val response = it.readText()
                Log.d("NOMINATIM_DEBUG", "Resposta Nominatim: $response")
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val name = obj.optString("display_name", "Mercado")
                    val latMercado = obj.getDouble("lat")
                    val lonMercado = obj.getDouble("lon")
                    mercados.add(name to GeoPoint(latMercado, lonMercado))
                }
            }
        } else {
            Log.e("NOMINATIM_ERROR", "Erro na requisição: ${conn.responseCode} ${conn.responseMessage}")
        }
    } catch (e: Exception) {
        Log.e("NOMINATIM_ERROR", "Erro ao buscar mercados: ${e.message}", e)
    } finally {
        conn?.disconnect()
    }
    return@withContext mercados
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLocalizacao(navController: NavController) {

    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
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
                controller.setCenter(GeoPoint(-23.55052, -46.633308)) // São Paulo
            }
        }
    } else null

    // Efeito para limpar marcadores quando a tela é recomposta.
    // Isso é opcional, mas pode ajudar a evitar marcadores duplicados.
    DisposableEffect(mapView) {
        onDispose {
            mapView?.overlays?.clear()
            mapView?.invalidate()
        }
    }

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
                                var enderecoResult: String?

                                if (input.matches(Regex("\d{5}-?\d{3}"))) {
                                    // Retrofit com suspend já usa o dispatcher correto
                                    val viaCepService = RetrofitFactory().getViaCepService()
                                    val resposta = viaCepService.buscarCep(input.replace("-", ""))
                                    enderecoResult = "${resposta.logradouro}, ${resposta.bairro}, ${resposta.localidade} - ${resposta.uf}"
                                } else {
                                    enderecoResult = input
                                }

                                if (isPreview) {
                                    endereco = enderecoResult
                                    feedbackMessage = "Endereço encontrado com sucesso!" to Color(0xFF006400)
                                    return@launch
                                }

                                val geo: List<Address>? = withContext(Dispatchers.IO) {
                                    try {
                                        Geocoder(context).getFromLocationName(enderecoResult, 1)
                                    } catch (e: IOException) {
                                        Log.e("GEOCODER_ERROR", "Erro no Geocoder: ${e.message}")
                                        null
                                    }
                                }

                                if (!geo.isNullOrEmpty()) {
                                    val loc = geo[0]
                                    val lat = loc.latitude
                                    val lon = loc.longitude
                                    val userPoint = GeoPoint(lat, lon)

                                    mapView!!.controller.animateTo(userPoint)
                                    mapView.overlays.clear()

                                    // Marcador para o usuário
                                    val userMarker = Marker(mapView)
                                    userMarker.position = userPoint
                                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    userMarker.title = "Você está aqui: $enderecoResult"
                                    mapView.overlays.add(userMarker)

                                    // Busca e adiciona marcadores para mercados
                                    val mercados = buscarMercadosProximos(lat, lon)
                                    if (mercados.isNotEmpty()) {
                                        Log.d("NOMINATIM_DEBUG", "Mercados retornados: $mercados")
                                        for ((nome, ponto) in mercados) {
                                            val markerMercado = Marker(mapView)
                                            markerMercado.position = ponto
                                            markerMercado.title = nome
                                            // Customize o ícone se desejar
                                            mapView.overlays.add(markerMercado)
                                        }
                                    } else {
                                        Log.d("NOMINATIM_DEBUG", "Nenhum mercado encontrado nas proximidades.")
                                    }

                                    mapView.invalidate()
                                    endereco = enderecoResult
                                    feedbackMessage = "Endereço encontrado com sucesso!" to Color(0xFF006400)
                                } else {
                                    throw Exception("Endereço não localizado no mapa.")
                                }

                            } catch (e: Exception) {
                                Log.e("SEARCH_ERROR", "Falha na busca: ${e.message}", e)
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
        BottomMenu(navController = navController, isAdmin = isAdmin)
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaLocalizacaoPreview() {
    InfoHub_telasTheme {
        TelaLocalizacao(rememberNavController())
    }
}
