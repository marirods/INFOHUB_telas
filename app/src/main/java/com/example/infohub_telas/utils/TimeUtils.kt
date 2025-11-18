package com.example.infohub_telas.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Função utilitária para obter o horário atual formatado
 */
fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date())
}
