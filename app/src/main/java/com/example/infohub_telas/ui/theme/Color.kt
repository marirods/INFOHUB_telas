package com.example.infohub_telas.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Brand Colors
val PrimaryOrange = Color(0xFFF9A01B)
val SecondaryOrange = Color(0xFFFF9800)
val PrimaryGreen = Color(0xFF4CAF50)
val PrimaryRed = Color(0xFFE57373)
val TertiaryOrange = Color(0xFFFF5722)

// Background and Surface Colors
val BackgroundGray = Color(0xFFF5F5F5)
val OnSurfaceGray = Color(0xFF757575)
val DarkText = Color(0xFF333333)

// Status Colors
val StatusActive = PrimaryGreen
val StatusInactive = PrimaryRed
val StatusPending = PrimaryOrange

// Chart Colors
val ChartBlue = Color(0xFF2196F3)
val ChartGreen = PrimaryGreen
val ChartOrange = PrimaryOrange
val ChartPurple = Color(0xFF9C27B0)

// Light Theme Colors
val primaryLight = PrimaryOrange
val onPrimaryLight = Color.White
val secondaryLight = SecondaryOrange
val onSecondaryLight = Color.White
val tertiaryLight = TertiaryOrange
val onTertiaryLight = Color.White
val backgroundLight = BackgroundGray
val onBackgroundLight = DarkText
val surfaceLight = Color.White
val onSurfaceLight = DarkText
val primaryContainerLight = PrimaryOrange.copy(alpha = 0.1f)
val onPrimaryContainerLight = PrimaryOrange
val errorLight = Color(0xFFB00020)
val onErrorLight = Color.White

// Dark Theme Colors
val primaryDark = PrimaryOrange
val onPrimaryDark = Color.White
val secondaryDark = SecondaryOrange
val onSecondaryDark = Color.White
val tertiaryDark = TertiaryOrange
val onTertiaryDark = Color.White
val backgroundDark = Color(0xFF121212)
val onBackgroundDark = Color.White
val surfaceDark = Color(0xFF1E1E1E)
val onSurfaceDark = Color.White
val primaryContainerDark = PrimaryOrange.copy(alpha = 0.2f)
val onPrimaryContainerDark = PrimaryOrange
val errorDark = Color(0xFFCF6679)
val onErrorDark = Color.White
