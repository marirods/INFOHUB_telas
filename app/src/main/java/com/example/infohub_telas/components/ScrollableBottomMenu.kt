package com.example.infohub_telas.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

/**
 * Hook para detectar direção da rolagem em LazyListState
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Hook para controlar visibilidade do menu baseado na rolagem
 */
@Composable
fun LazyListState.rememberMenuVisibility(): Boolean {
    val isScrollingUp = isScrollingUp()
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isScrollingUp) {
        // Pequeno delay para evitar mudanças muito rápidas
        kotlinx.coroutines.delay(50)
        isVisible = isScrollingUp || firstVisibleItemIndex == 0
    }

    return isVisible
}

/**
 * Hook para detectar direção da rolagem em ScrollState (para verticalScroll)
 */
@Composable
fun ScrollState.rememberMenuVisibility(): Boolean {
    var isVisible by remember { mutableStateOf(true) }
    var lastScrollValue by remember { mutableIntStateOf(0) }

    LaunchedEffect(value) {
        val currentScroll = value
        val delta = currentScroll - lastScrollValue

        // Se rolou mais de 50px, considera mudança de direção
        if (kotlin.math.abs(delta) > 50) {
            isVisible = delta < 0 || currentScroll == 0 // Mostra quando rola para cima ou está no topo
        }

        lastScrollValue = currentScroll
    }

    return isVisible
}

/**
 * Hook para controlar visibilidade do menu em LazyVerticalGrid baseado na rolagem
 */
@Composable
fun LazyGridState.rememberMenuVisibility(): Boolean {
    val isScrollingUp = isScrollingUp()
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isScrollingUp) {
        // Pequeno delay para evitar mudanças muito rápidas
        kotlinx.coroutines.delay(50)
        isVisible = isScrollingUp || firstVisibleItemIndex == 0
    }

    return isVisible
}

/**
 * Hook para detectar direção da rolagem em LazyGridState
 */
@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Menu inferior com animação suave baseada na rolagem
 */
@Composable
fun AnimatedScrollableBottomMenu(
    navController: NavController?,
    isAdmin: Boolean = false,
    isVisible: Boolean = true,
    extraBottomPadding: Dp = 0.dp,
    userPerfil: String? = null
) {
    // Animação suave de translação Y
    val translationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 200f,
        animationSpec = tween(
            durationMillis = 300,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "menu_translation"
    )

    // Animação de opacidade para efeito mais suave
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 250,
            easing = androidx.compose.animation.core.LinearEasing
        ),
        label = "menu_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.translationY = translationY
                this.alpha = alpha
            }
            .padding(bottom = extraBottomPadding)
            .zIndex(10f)
    ) {
        BottomMenu(
            navController = navController,
            isAdmin = isAdmin,
            userPerfil = userPerfil
        )
    }
}
