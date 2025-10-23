package com.example.infohub_telas.telas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face

import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OpenScreen(navController: NavController) {
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9F1C))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                navController.navigate("welcome")
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (showContent) {
                LogoContent()
            }
        }
    }
}

@Composable
fun LogoContent() {
    val offsetXCart = remember { Animatable(1000f) }
    val offsetXText = remember { Animatable(1000f) }
    val alphaText = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            offsetXCart.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = EaseOutCubic)
            )
        }

        delay(300)

        launch {
            offsetXText.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = EaseOutCubic)
            )
        }
        launch {
            alphaText.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600)
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.carrinhoparaanimar),
            contentDescription = "Carrinho",
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    translationX = offsetXCart.value
                    translationY = offsetY
                }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.graphicsLayer {
                translationX = offsetXText.value
                alpha = alphaText.value
            }
        ) {
            Text(
                text = "INFO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "HUB",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9F1C)
            )
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    val logoOffsetY = remember { Animatable(0f) }
    val circleSize = remember { Animatable(250f) }
    val logoScale = remember { Animatable(1f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val feature1Alpha = remember { Animatable(0f) }
    val feature2Alpha = remember { Animatable(0f) }
    val feature3Alpha = remember { Animatable(0f) }
    val feature1OffsetX = remember { Animatable(-100f) }
    val feature2OffsetX = remember { Animatable(-100f) }
    val feature3OffsetX = remember { Animatable(-100f) }

    LaunchedEffect(Unit) {
        launch {
            logoOffsetY.animateTo(
                targetValue = -100f, // Lowered the image even more
                animationSpec = tween(durationMillis = 800, easing = EaseInOutCubic)
            )
        }
        launch {
            circleSize.animateTo(
                targetValue = 180f,
                animationSpec = tween(durationMillis = 800, easing = EaseInOutCubic)
            )
        }
        launch {
            logoScale.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(durationMillis = 800, easing = EaseInOutCubic)
            )
        }

        delay(400)

        launch {
            titleAlpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 600))
        }
        delay(200)
        launch {
            subtitleAlpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 600))
        }

        delay(300)

        launch {
            feature1Alpha.animateTo(1f, tween(500))
            feature1OffsetX.animateTo(0f, tween(500, easing = EaseOutCubic))
        }
        delay(200)
        launch {
            feature2Alpha.animateTo(1f, tween(500))
            feature2OffsetX.animateTo(0f, tween(500, easing = EaseOutCubic))
        }
        delay(200)
        launch {
            feature3Alpha.animateTo(1f, tween(500))
            feature3OffsetX.animateTo(0f, tween(500, easing = EaseOutCubic))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9F1C))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                navController.navigate("loginCadastro")
            }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (-50).dp, y = (-50).dp)
                .background(Color(0xFFFFB347), shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 80.dp, y = 80.dp)
                .background(Color(0xFFFFB347), shape = CircleShape)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
                    .size(circleSize.value.dp)
                    .graphicsLayer { translationY = logoOffsetY.value }
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer {
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carrinhoparaanimar),
                        contentDescription = "Carrinho",
                        modifier = Modifier.size(70.dp),
                        colorFilter = ColorFilter.tint(Color(0xFFFF9F1C))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(text = "INFO", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text(text = "HUB", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9F1C))
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "BEM VINDO AO INFOHUB",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .graphicsLayer { alpha = titleAlpha.value }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "O CAMINHO MAIS CURTO PARA AS\nMELHORES PROMOÇÕES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .graphicsLayer { alpha = subtitleAlpha.value }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                FeatureItem(
                    icon = Icons.Default.LocationOn,
                    text = "Achamos as melhores\npromoções pertinho de você !",
                    alpha = feature1Alpha.value,
                    offsetX = feature1OffsetX.value
                )
                FeatureItem(
                    icon = Icons.Default.Face,
                    text = "I.A responsável por verificar as\nmelhores preços, nada passa\nbatido !",
                    alpha = feature2Alpha.value,
                    offsetX = feature2OffsetX.value
                )
                FeatureItem(
                    icon = Icons.Default.Menu,
                    text = "Lista de compras inteligente,\ncom o foco em fazer você\neconomizar mais !",
                    alpha = feature3Alpha.value,
                    offsetX = feature3OffsetX.value
                )
            }
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    text: String,
    alpha: Float,
    offsetX: Float
) {
    Surface(
        color = Color.Black.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .graphicsLayer {
                this.alpha = alpha
                translationX = offsetX
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color.White,
                lineHeight = 20.sp
            )
        }
    }
}