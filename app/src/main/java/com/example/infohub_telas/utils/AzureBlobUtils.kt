package com.example.infohub_telas.utils

import android.util.Log

/**
 * Utilitário para trabalhar com Azure Blob Storage
 * Container: infohubfotos
 * Blob SAS Token: sp=cw&st=2025-11-27T02:49:12Z&se=2025-12-12T15:00:00Z&sv=2024-11-04&sr=c&sig=KwdC4ievGnVlKJSDHjwRongfdcaq8kCW%2BU8KssM1F%2Bo%3D
 */
object AzureBlobUtils {

    private const val STORAGE_ACCOUNT = "infohubstorage"
    private const val CONTAINER_NAME = "infohubfotos"
    private const val SAS_TOKEN = "sp=cw&st=2025-11-27T02:49:12Z&se=2025-12-12T15:00:00Z&sv=2024-11-04&sr=c&sig=KwdC4ievGnVlKJSDHjwRongfdcaq8kCW%2BU8KssM1F%2Bo%3D"

    // Base URL do blob storage
    private const val BASE_URL = "https://$STORAGE_ACCOUNT.blob.core.windows.net/$CONTAINER_NAME"

    /**
     * Constrói a URL completa para acessar uma imagem no Azure Blob Storage
     * @param imageName Nome do arquivo de imagem (ex: "produto_123.jpg")
     * @return URL completa com SAS token para acessar a imagem
     */
    fun getImageUrl(imageName: String?): String? {
        if (imageName.isNullOrBlank()) {
            Log.w("AzureBlobUtils", "⚠️ Nome da imagem está vazio ou nulo")
            return null
        }

        // Se já for uma URL completa (http/https), retorna como está
        if (imageName.startsWith("http://") || imageName.startsWith("https://")) {
            Log.d("AzureBlobUtils", "🌐 URL completa detectada: $imageName")
            return imageName
        }

        // Remove barras no início do nome se houver
        val cleanImageName = imageName.trimStart('/')

        // Verifica se o nome tem uma extensão válida
        if (!cleanImageName.contains('.')) {
            Log.w("AzureBlobUtils", "⚠️ Nome de imagem sem extensão: $cleanImageName")
            // Adiciona extensão padrão se não houver
            val imageWithExtension = "$cleanImageName.jpg"
            val fullUrl = "$BASE_URL/$imageWithExtension?$SAS_TOKEN"
            Log.d("AzureBlobUtils", "📸 URL gerada com extensão padrão: $fullUrl")
            return fullUrl
        }

        // Constrói a URL completa
        val fullUrl = "$BASE_URL/$cleanImageName?$SAS_TOKEN"

        Log.d("AzureBlobUtils", "📸 URL gerada para imagem '$cleanImageName': $fullUrl")
        return fullUrl
    }

    /**
     * Constrói a URL de upload para o Azure Blob Storage
     * @param imageName Nome do arquivo de imagem
     * @return URL completa para upload (PUT)
     */
    fun getUploadUrl(imageName: String): String {
        val cleanImageName = imageName.trimStart('/')
        return "$BASE_URL/$cleanImageName?$SAS_TOKEN"
    }

    /**
     * Valida se uma URL do Azure Blob é válida
     * @param url URL para validar
     * @return true se for uma URL válida do Azure Blob Storage
     */
    fun isValidAzureBlobUrl(url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        return url.contains(STORAGE_ACCOUNT) && url.contains(CONTAINER_NAME)
    }

    /**
     * Extrai o nome do arquivo de uma URL do Azure Blob
     * @param url URL completa do blob
     * @return Nome do arquivo ou null se inválido
     */
    fun extractImageNameFromUrl(url: String?): String? {
        if (url.isNullOrBlank()) return null

        try {
            // Remove o SAS token
            val urlWithoutToken = url.substringBefore("?")
            // Pega apenas o nome do arquivo
            return urlWithoutToken.substringAfterLast("/")
        } catch (e: Exception) {
            Log.e("AzureBlobUtils", "Erro ao extrair nome da imagem: ${e.message}")
            return null
        }
    }

    /**
     * Gera um nome único para uma imagem de produto
     * @param productId ID do produto
     * @param extension Extensão do arquivo (padrão: jpg)
     * @return Nome único para o arquivo
     */
    fun generateProductImageName(productId: Int, extension: String = "jpg"): String {
        val timestamp = System.currentTimeMillis()
        return "produto_${productId}_${timestamp}.$extension"
    }

    /**
     * Imagem placeholder padrão quando não há imagem disponível
     */
    fun getPlaceholderImageUrl(): String {
        return "https://via.placeholder.com/400x300/E0E0E0/757575?text=InfoHub%0AProduto"
    }

    /**
     * Testa se uma URL do Azure Blob Storage é acessível
     * @param url URL para testar
     * @return true se a URL for válida e acessível
     */
    fun testImageUrl(url: String?): Boolean {
        if (url.isNullOrBlank()) return false

        return try {
            // Verifica se é uma URL válida do Azure Blob Storage
            url.contains(STORAGE_ACCOUNT) &&
                    url.contains(CONTAINER_NAME) &&
                    url.startsWith("https://")
        } catch (e: Exception) {
            Log.e("AzureBlobUtils", "Erro ao validar URL: ${e.message}")
            false
        }
    }

    /**
     * Formata nomes de arquivo para garantir compatibilidade
     * @param fileName Nome do arquivo original
     * @return Nome formatado e seguro
     */
    fun sanitizeFileName(fileName: String): String {
        return fileName
            .replace(" ", "_")
            .replace("[^a-zA-Z0-9._-]".toRegex(), "")
            .lowercase()
    }

    /**
     * Log de debug para todas as URLs geradas
     */
    fun logImageUrlDebug(productName: String?, imageName: String?, finalUrl: String?) {
        Log.d("AzureBlobUtils", """
            🔍 DEBUG INFO:
            - Produto: $productName
            - Nome da imagem: $imageName  
            - URL final: $finalUrl
            - É válida: ${testImageUrl(finalUrl)}
        """.trimIndent())
    }
}
