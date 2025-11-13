package com.example.infohub_telas.utils

object ApiUtils {
    fun formatErrorMessage(error: String?): String = when {
        error == null -> "Erro desconhecido"
        error.contains("timeout", true) -> "Tempo de resposta excedido"
        error.contains("network", true) -> "Falha de conexão"
        else -> error
    }

    fun formatInfoCashPoints(points: Int): String = when {
        points >= 1_000_000 -> String.format("%.1fM HC", points / 1_000_000.0)
        points >= 1_000 -> String.format("%.1fK HC", points / 1_000.0)
        else -> "$points HC"
    }

    fun calculateLevel(points: Int, perLevel: Int = 500): Int = (points / perLevel) + 1

    fun calculateProgress(points: Int, perLevel: Int = 500): Float {
        val current = points % perLevel
        return current.toFloat() / perLevel.toFloat()
    }

    fun pointsToNextLevel(points: Int, perLevel: Int = 500): Int = perLevel - (points % perLevel)
}
