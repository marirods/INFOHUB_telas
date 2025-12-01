package com.example.infohub_telas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.infohub_telas.R

/**
 * Seção de Estabelecimentos Registrados para a TelaLocalizacao
 */
@Composable
fun EstabelecimentosRegistradosSection(
    estabelecimentos: List<com.example.infohub_telas.model.EstabelecimentoComEndereco>,
    onEstabelecimentoClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Cabeçalho da seção
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Store,
                    contentDescription = null,
                    tint = Color(0xFFF9A01B),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Estabelecimentos Registrados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "${estabelecimentos.size} estabelecimento${if (estabelecimentos.size != 1) "s" else ""}",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista horizontal de estabelecimentos
        if (estabelecimentos.isEmpty()) {
            // Mensagem quando não há estabelecimentos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.StoreMallDirectory,
                        contentDescription = null,
                        tint = Color(0xFFCCCCCC),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nenhum estabelecimento registrado",
                        color = Color(0xFF999999),
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(estabelecimentos) { estabelecimento ->
                    EstabelecimentoCard(
                        estabelecimento = estabelecimento,
                        onClick = {
                            onEstabelecimentoClick(estabelecimento.id_estabelecimento)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Card individual de estabelecimento - SIMPLIFICADO
 * Mostra apenas: Imagem + Nome
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstabelecimentoCard(
    estabelecimento: com.example.infohub_telas.model.EstabelecimentoComEndereco,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem do estabelecimento
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                if (!estabelecimento.foto_url.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(com.example.infohub_telas.utils.AzureBlobUtils.getImageUrl(estabelecimento.foto_url))
                            .crossfade(true)
                            .build(),
                        contentDescription = estabelecimento.nome,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.jara)
                    )
                } else {
                    // Placeholder quando não há imagem
                    Icon(
                        Icons.Default.Store,
                        contentDescription = null,
                        tint = Color(0xFFCCCCCC),
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // Nome do estabelecimento (centralizado)
            Text(
                text = estabelecimento.nome,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}

