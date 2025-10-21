package com.example.infohub_telas.telas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TelaLoginCadastro(navController: NavController) {
    // Animações
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.5f) }
    val lineAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val loginButtonAlpha = remember { Animatable(0f) }
    val loginButtonOffsetY = remember { Animatable(50f) }
    val cadastroButtonAlpha = remember { Animatable(0f) }
    val cadastroButtonOffsetY = remember { Animatable(50f) }
    val separatorAlpha = remember { Animatable(0f) }
    val socialButtonsAlpha = remember { Animatable(0f) }
    val bottomLineAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo aparece com fade e escala
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600)
            )
        }
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600, easing = EaseOutBack)
            )
        }

        delay(300)

        // Linha verde acima do título
        launch {
            lineAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400)
            )
        }

        delay(200)

        // Título
        launch {
            titleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        delay(150)

        // Subtítulo
        launch {
            subtitleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        delay(300)

        // Botão LOGIN
        launch {
            loginButtonAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        launch {
            loginButtonOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = EaseOutCubic)
            )
        }

        delay(200)

        // Botão CADASTRE-SE
        launch {
            cadastroButtonAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        launch {
            cadastroButtonOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = EaseOutCubic)
            )
        }

        delay(200)

        // Separador "ou entre com"
        launch {
            separatorAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400)
            )
        }

        delay(200)

        // Botões sociais
        launch {
            socialButtonsAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        delay(200)

        // Linha verde inferior
        launch {
            bottomLineAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9F1C))
    ) {
        // Círculo decorativo superior esquerdo
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-80).dp, y = (-80).dp)
                .background(Color(0xFFFFB347), shape = CircleShape)
        )

        // Círculo decorativo inferior direito
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 100.dp, y = 100.dp)
                .background(Color(0xFFFFB347), shape = CircleShape)
        )

        // Coluna centralizada verticalmente
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center  // ← ADICIONADO: Centraliza verticalmente
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .graphicsLayer {
                        alpha = logoAlpha.value
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                    }
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carrinhoparaanimar),
                        contentDescription = "Carrinho",
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Text(
                            text = "INFO",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "HUB",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9F1C)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Linha verde superior
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .graphicsLayer { alpha = lineAlpha.value }
                    .background(Color(0xFF2E7D32), shape = RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "FAZER COMPRAS PODE SER SIMPLES",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { alpha = titleAlpha.value }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "Lugar para fazer compras rapidamente",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { alpha = subtitleAlpha.value }
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Botão LOGIN
            Button(
                onClick = { /* navController.navigate("login") */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .graphicsLayer {
                        alpha = loginButtonAlpha.value
                        translationY = loginButtonOffsetY.value
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão CADASTRE-SE
            Button(
                onClick = { /* navController.navigate("cadastro") */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .graphicsLayer {
                        alpha = cadastroButtonAlpha.value
                        translationY = cadastroButtonOffsetY.value
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "CADASTRE-SE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Separador "ou entre com"
            Text(
                text = "ou entre com",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.graphicsLayer { alpha = separatorAlpha.value }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de redes sociais
            Row(
                modifier = Modifier
                    .graphicsLayer { alpha = socialButtonsAlpha.value },
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Botão Apple
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = "Apple Login",
                        modifier = Modifier.size(28.dp),
                        tint = Color.Black
                    )
                }

                // Botão Google
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Login",
                        modifier = Modifier.size(28.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Linha verde inferior
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .graphicsLayer { alpha = bottomLineAlpha.value }
                    .background(Color(0xFF2E7D32), shape = RoundedCornerShape(2.dp))
            )
        }
    }
}