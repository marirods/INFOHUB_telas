package com.example.infohub_telas.telas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.infohub_telas.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenScreen()
        }
    }
}

@Composable
fun OpenScreen() {
    var showContent by remember { mutableStateOf(false) }

    // Delay to show content after the white circle appears
    LaunchedEffect(Unit) {
        delay(500) // Slightly shorter delay for a snappier feel
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9F1C)), // Orange color
        contentAlignment = Alignment.Center
    ) {
        // White circle
        Box(
            modifier = Modifier
                .size(250.dp) // Slightly larger circle
                .background(Color.White, shape = androidx.compose.foundation.shape.CircleShape),
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
    // Separate animatables for cart and text
    val offsetXCart = remember { Animatable(1000f) } // Cart starts off-screen
    val offsetXText = remember { Animatable(1000f) } // Text starts off-screen
    val alphaText = remember { Animatable(0f) }      // Text starts transparent

    // Staggered animations
    LaunchedEffect(Unit) {
        // Launch cart animation immediately
        launch {
            offsetXCart.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = EaseOutCubic)
            )
        }

        // Wait before starting text animations
        delay(300)

        // Launch text slide and fade-in animations
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

    // Floating animation for the cart
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f, // Increased floating distance
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
        // Cart icon (larger)
        Image(
            painter = painterResource(id = R.drawable.carrinhoparaanimar),
            contentDescription = "Carrinho",
            modifier = Modifier
                .size(100.dp) // Increased size
                .graphicsLayer {
                    translationX = offsetXCart.value
                    translationY = offsetY
                }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Texto INFOHUB
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
                color = Color(0xFFFF9F1C) // Cor laranja
            )
        }
    }
}
