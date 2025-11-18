package com.example.infohub_telas.telas

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import androidx.compose.foundation.rememberScrollState
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.config.Configuration
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.content.Context
import com.example.infohub_telas.components.MenuComponent


// Função para criar alfinete vermelho
fun criarIconeAlfinete(context: Context): BitmapDrawable {
    val size = 90
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    
    val paint = Paint().apply {
        color = android.graphics.Color.parseColor("#D32F2F")
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    

    val path = android.graphics.Path()
    val centerX = size / 2f
    val topY = size * 0.15f
    val bottomY = size * 0.85f
    val radius = size * 0.25f
    

    path.addCircle(centerX, topY + radius, radius, android.graphics.Path.Direction.CW)
    
    // Triângulo inferior
    path.moveTo(centerX - radius * 0.7f, topY + radius * 1.5f)
    path.lineTo(centerX, bottomY)
    path.lineTo(centerX + radius * 0.7f, topY + radius * 1.5f)
    path.close()
    
    canvas.drawPath(path, paint)
    
    // Desenha círculo branco interno
    val paintInterno = Paint().apply {
        color = android.graphics.Color.WHITE
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    canvas.drawCircle(centerX, topY + radius, radius * 0.4f, paintInterno)
    
    return BitmapDrawable(context.resources, bitmap)
}

// Função para ícone de carrinho branco com círculo laranja
fun criarIconeCarrinho(context: Context): BitmapDrawable {
    val size = 70
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    
    // Desenha círculo de fundo laranja claro
    val paintFundo = Paint().apply {
        color = android.graphics.Color.parseColor("#FFB74D")
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    canvas.drawCircle(size / 2f, size / 2f, size / 2.5f, paintFundo)
    

    val paintBorda = Paint().apply {
        color = android.graphics.Color.WHITE
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    canvas.drawCircle(size / 2f, size / 2f, size / 2.5f, paintBorda)
    

    val paintTexto = Paint().apply {
        color = android.graphics.Color.WHITE // Carrinho branco
        textSize = 35f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    
    val xPos = size / 2f
    val yPos = (size / 2f) - ((paintTexto.descent() + paintTexto.ascent()) / 2)
    canvas.drawText("🛒", xPos, yPos, paintTexto)
    
    return BitmapDrawable(context.resources, bitmap)
}

data class Estabelecimento(
    val nome: String,
    val tipo: String,
    val localizacao: GeoPoint,
    var distancia: Double = 0.0,
    val endereco: String = "",
    val numero: String = "",
    val bairro: String = "",
    val rating: Float = 0f,
    val totalReviews: Int = 0,
    val photoUrl: String = "",
    val comentarios: List<String> = emptyList()
)

// Calcular distância entre dois pontos (em km)
fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Raio da Terra em km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return R * c
}


suspend fun buscarMercadosProximos(lat: Double, lon: Double, raioKm: Double = 2.0):
        List<Estabelecimento> = withContext(Dispatchers.IO) {
    val estabelecimentos = mutableListOf<Estabelecimento>()
    var conn: HttpURLConnection? = null
    try {

        val raioMetros = (raioKm * 1000).toInt()
        
        val overpassQuery = """
            [out:json][timeout:25];
            (
              node["shop"="supermarket"](around:$raioMetros,$lat,$lon);
              way["shop"="supermarket"](around:$raioMetros,$lat,$lon);
              node["shop"="convenience"](around:$raioMetros,$lat,$lon);
              way["shop"="convenience"](around:$raioMetros,$lat,$lon);
              node["shop"="grocery"](around:$raioMetros,$lat,$lon);
              way["shop"="grocery"](around:$raioMetros,$lat,$lon);
            );
            out center;
        """.trimIndent()

        val encodedQuery = URLEncoder.encode(overpassQuery, "UTF-8")
        val urlStr = "https://overpass-api.de/api/interpreter?data=$encodedQuery"

        Log.d("OVERPASS_DEBUG", "Buscando estabelecimentos próximos em raio de $raioKm km")

        val url = URL(urlStr)
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("User-Agent", "InfoHubApp/1.0")
        conn.connectTimeout = 25000
        conn.readTimeout = 25000
        conn.connect()

        if (conn.responseCode == HttpURLConnection.HTTP_OK) {
            conn.inputStream.bufferedReader().use {
                val response = it.readText()
                Log.d("OVERPASS_DEBUG", "Resposta recebida com sucesso")
                
                val jsonObject = org.json.JSONObject(response)
                val elements = jsonObject.getJSONArray("elements")

                for (i in 0 until elements.length()) {
                    val element = elements.getJSONObject(i)
                    val tags = element.optJSONObject("tags")
                    
                    if (tags != null) {
                        val nome = tags.optString("name", "Estabelecimento sem nome")
                        val shopType = tags.optString("shop", "desconhecido")
                        
                        val tipo = when (shopType) {
                            "supermarket" -> "Supermercado"
                            "convenience" -> "Mercado"
                            "grocery" -> "Mercearia"
                            else -> "Comércio"
                        }
                        

                        val latEstab: Double
                        val lonEstab: Double
                        
                        if (element.has("lat") && element.has("lon")) {

                            latEstab = element.getDouble("lat")
                            lonEstab = element.getDouble("lon")
                        } else if (element.has("center")) {

                            val center = element.getJSONObject("center")
                            latEstab = center.getDouble("lat")
                            lonEstab = center.getDouble("lon")
                        } else {
                            continue
                        }
                        

                        val street = tags.optString("addr:street", "Rua não informada")
                        val houseNumber = tags.optString("addr:housenumber", "S/N")
                        val suburb = tags.optString("addr:suburb", tags.optString("addr:neighbourhood", "Bairro não informado"))
                        val city = tags.optString("addr:city", "São Paulo")
                        
                        val enderecoCompleto = "$street, $houseNumber - $suburb, $city"

                        val photoSeed = (latEstab * 1000 + lonEstab * 1000).toInt().toString()
                        val photoUrl = when (shopType) {
                            "supermarket" -> "https://source.unsplash.com/400x300/?supermarket,grocery&sig=$photoSeed"
                            "convenience" -> "https://source.unsplash.com/400x300/?convenience,store&sig=$photoSeed"
                            "grocery" -> "https://source.unsplash.com/400x300/?market,food&sig=$photoSeed"
                            else -> "https://source.unsplash.com/400x300/?shop,store&sig=$photoSeed"
                        }
                        

                        val comentariosPossiveis = listOf(
                            "Ótimo atendimento!",
                            "Preços justos e produtos frescos",
                            "Sempre encontro o que preciso",
                            "Ambiente limpo e organizado",
                            "Variedade de produtos",
                            "Bom custo-benefício",
                            "Atendimento rápido",
                            "Produtos de qualidade"
                        )
                        val numComentarios = (1..3).random()
                        val comentarios = comentariosPossiveis.shuffled().take(numComentarios)
                        
                        estabelecimentos.add(
                            Estabelecimento(
                                nome = nome,
                                tipo = tipo,
                                localizacao = GeoPoint(latEstab, lonEstab),
                                endereco = street,
                                numero = houseNumber,
                                bairro = suburb,
                                rating = (3.5f + Math.random().toFloat() * 1.5f).coerceIn(0f, 5f),
                                totalReviews = (10..500).random(),
                                photoUrl = photoUrl,
                                comentarios = comentarios
                            )
                        )
                    }
                }
                
                Log.d("OVERPASS_DEBUG", "${estabelecimentos.size} estabelecimentos encontrados")
            }
        } else {
            Log.e("OVERPASS_ERROR", "Erro na requisição: ${conn.responseCode} ${conn.responseMessage}")
        }
    } catch (e: Exception) {
        Log.e("OVERPASS_ERROR", "Erro ao buscar estabelecimentos: ${e.message}", e)
    } finally {
        conn?.disconnect()
    }
    return@withContext estabelecimentos
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLocalizacao(navController: NavController) {

    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val scope = rememberCoroutineScope()

    // Estado para controlar rolagem e visibilidade do menu
    val scrollState = rememberScrollState()
    val isMenuVisible = scrollState.rememberMenuVisibility()

    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf<String?>(null) }
    var feedbackMessage by remember { mutableStateOf<Pair<String, Color>?>(null) }
    var estabelecimentosEncontrados by remember { mutableStateOf<List<Estabelecimento>>(emptyList()) }
    var localizacaoUsuario by remember { mutableStateOf<GeoPoint?>(null) }
    var estabelecimentoSelecionado by remember { mutableStateOf<Estabelecimento?>(null) }

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


    DisposableEffect(mapView) {
        onDispose {
            mapView?.overlays?.clear()
            mapView?.invalidate()
        }
    }

    // Box externo para conter todos os composables da tela
    Box(modifier = Modifier.fillMaxSize()) {
        // Box principal com conteúdo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9A01B))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuComponent(navController = navController)
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

            Spacer(modifier = Modifier
                .height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
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
                            feedbackMessage = null
                        },
                        placeholder = { Text("Digite um endereço ou CEP (ex: 01310-100...)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFEEEEEE)
                        )
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

                                    // Salva localização do usuário
                                    localizacaoUsuario = userPoint
                                    
                                    // Marcador com alfinete vermelho para o usuário
                                    val userMarker = Marker(mapView)
                                    userMarker.position = userPoint
                                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    userMarker.title = "📍 Você está aqui"
                                    userMarker.snippet = enderecoResult
                                    userMarker.icon = criarIconeAlfinete(context)
                                    mapView.overlays.add(userMarker)


                                    val estabelecimentos = buscarMercadosProximos(lat, lon, raioKm = 5.0)
                                    if (estabelecimentos.isNotEmpty()) {
                                        Log.d("OVERPASS_DEBUG", "${estabelecimentos.size} estabelecimentos encontrados")
                                        

                                        estabelecimentos.forEach { estab ->
                                            estab.distancia = calcularDistancia(
                                                lat, lon,
                                                estab.localizacao.latitude,
                                                estab.localizacao.longitude
                                            )
                                        }
                                        val estabelecimentosOrdenados = estabelecimentos.sortedBy { it.distancia }
                                        estabelecimentosEncontrados = estabelecimentosOrdenados
                                        
                                        for (estab in estabelecimentosOrdenados) {
                                            val markerEstab = Marker(mapView)
                                            markerEstab.position = estab.localizacao
                                            markerEstab.title = "${estab.tipo}: ${estab.nome}"
                                            markerEstab.snippet = "Distância: %.2f km".format(estab.distancia)
                                            markerEstab.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                            markerEstab.icon = criarIconeCarrinho(context)
                                            mapView.overlays.add(markerEstab)
                                        }
                                        feedbackMessage = "${estabelecimentos.size} estabelecimentos encontrados!" to Color(0xFF006400)
                                    } else {
                                        Log.d("OVERPASS_DEBUG", "Nenhum estabelecimento encontrado nas proximidades.")
                                        estabelecimentosEncontrados = emptyList()
                                        feedbackMessage = "Endereço encontrado, mas nenhum estabelecimento próximo." to Color(0xFFFFA500)
                                    }

                                    mapView.invalidate()
                                    endereco = enderecoResult
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
                

                if (estabelecimentosEncontrados.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        estabelecimentosEncontrados.forEach { estab ->
                            val interactionSource = remember { MutableInteractionSource() }
                            val isHovered by interactionSource.collectIsHoveredAsState()
                            val scale by animateFloatAsState(
                                targetValue = if (isHovered) 1.05f else 1f,
                                label = "card_scale"
                            )
                            
                            // Animação de clique
                            val clickScale = remember { Animatable(1f) }
                            val coroutineScope = rememberCoroutineScope()
                            
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scale(scale * clickScale.value)
                                    .clickable(interactionSource = interactionSource, indication = null) {
                                        coroutineScope.launch {
                                            // Animação de "pulse" ao clicar
                                            clickScale.animateTo(
                                                targetValue = 0.95f,
                                                animationSpec = tween(durationMillis = 100)
                                            )
                                            clickScale.animateTo(
                                                targetValue = 1.05f,
                                                animationSpec = tween(durationMillis = 100)
                                            )
                                            clickScale.animateTo(
                                                targetValue = 1f,
                                                animationSpec = tween(durationMillis = 100)
                                            )
                                            // Abre o modal após a animação
                                            estabelecimentoSelecionado = estab
                                        }
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Foto do estabelecimento
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(estab.photoUrl)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "Foto de ${estab.nome}",
                                            modifier = Modifier
                                                .size(60.dp)
                                                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        
                                        Spacer(modifier = Modifier.width(12.dp))
                                        
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = estab.nome,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                maxLines = 1,
                                                style = TextStyle(fontWeight = FontWeight.Bold)
                                            )
                                            Text(
                                                text = estab.tipo,
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                repeat(5) { index ->
                                                    Text(
                                                        text = if (index < estab.rating.toInt()) "⭐" else "☆",
                                                        fontSize = 10.sp,
                                                        color = if (index < estab.rating.toInt()) Color(0xFFFFC107) else Color.Gray
                                                    )
                                                }
                                                Text(
                                                    text = " %.1f".format(estab.rating),
                                                    fontSize = 11.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }
                                    

                                    if (estab.comentarios.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(8.dp)) {
                                                Text(
                                                    text = "💬 Comentários:",
                                                    fontSize = 11.sp,
                                                    color = Color.Gray,
                                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                estab.comentarios.take(2).forEach { comentario ->
                                                    Text(
                                                        text = "• $comentario",
                                                        fontSize = 11.sp,
                                                        color = Color.DarkGray,
                                                        maxLines = 1
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                }
            }
        }
        } // Fecha o Box principal

        // Menu inferior animado - dentro do Box externo
        AnimatedScrollableBottomMenu(
            navController = navController,
            isAdmin = isAdmin,
            isVisible = isMenuVisible
        )

        estabelecimentoSelecionado?.let { estab ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { estabelecimentoSelecionado = null },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
                    .clickable(enabled = false) { },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = estab.tipo,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        IconButton(onClick = { estabelecimentoSelecionado = null }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar",
                                tint = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    

                    if (estab.photoUrl.isNotEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(estab.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Foto de ${estab.nome}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val emoji = when (estab.tipo) {
                                "Supermercado" -> "🛒"
                                "Mercado" -> "🏪"
                                "Mercearia" -> "🛍"
                                else -> "📍"
                            }
                            Text(
                                text = emoji,
                                fontSize = 80.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Nome do estabelecimento
                    Text(
                        text = estab.nome,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Avaliação
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Estrela",
                                tint = if (index < estab.rating) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "%.1f".format(estab.rating),
                            fontSize = 16.sp,
                            color = Color.Black,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = " (${estab.totalReviews} avaliações)",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Endereço Completo
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "📍",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Endereço Completo",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "${estab.endereco}, ${estab.numero}",
                                fontSize = 14.sp,
                                color = Color.Black,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = estab.bairro,
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Distância
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "📏",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Distância",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "%.2f km de você".format(estab.distancia),
                                fontSize = 14.sp,
                                color = Color(0xFFF9A01B)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Botão de ação
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF9A01B)
                        )
                    ) {
                        Text("Ver no Mapa")
                    }
                }
            }
        }
        } // Fecha o estabelecimentoSelecionado?.let
    } // Fecha o Box externo
} // Fecha a função TelaLocalizacao

// Função auxiliar para o menu (placeholder)
@Composable
fun MenuComponent(navController: NavController) {
    TODO("Not yet implemented")
}

// Preview da tela de localização
@Preview(showSystemUi = true)
@Composable
fun TelaLocalizacaoPreview() {
    InfoHub_telasTheme {
        TelaLocalizacao(rememberNavController())
    }
}
