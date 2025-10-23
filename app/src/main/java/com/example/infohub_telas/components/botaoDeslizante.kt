package com.example.infohub_telas.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotaoPessoaDeslizante(
    isPessoaFisica: Boolean,
    onPessoaFisicaClick: () -> Unit,
    onPessoaJuridicaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(8.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFF2F2F2))
            .fillMaxWidth()
    ) {
        val buttonWidth = maxWidth / 2

        val offset by animateDpAsState(
            targetValue = if (isPessoaFisica) 0.dp else buttonWidth,
            animationSpec = tween(durationMillis = 300),
            label = "offset"
        )

        // Sliding part
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .fillMaxHeight()
                .offset(x = offset)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF4CAF50))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onPessoaFisicaClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pessoa Física",
                    color = if (isPessoaFisica) Color.White else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onPessoaJuridicaClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pessoa Jurídica",
                    color = if (!isPessoaFisica) Color.White else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BotaoPessoaDeslizantePreview() {
    var isPessoaFisica by remember { mutableStateOf(true) }

    BotaoPessoaDeslizante(
        isPessoaFisica = isPessoaFisica,
        onPessoaFisicaClick = { isPessoaFisica = true },
        onPessoaJuridicaClick = { isPessoaFisica = false }
    )
}