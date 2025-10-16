package com.example.infohub_telas.service

import com.example.infohub_telas.model.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class InfoHubClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    private val baseUrl = "http://10.0.2.2:8080/v1/infohub/"

    suspend fun cadastrarUsuario(usuario: Usuario): Usuario {
        return client.post("${baseUrl}usuarios/cadastro") {
            contentType(ContentType.Application.Json)
            setBody(usuario)
        }.body()
    }

    suspend fun logarUsuario(loginRequest: LoginRequest): LoginResponse {
        return client.post("${baseUrl}login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    suspend fun recuperarSenha(request: RecuperarSenhaRequest): RecuperarSenhaResponse {
        return client.post("${baseUrl}recuperar-senha") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun validarCodigo(request: ValidarCodigoRequest): ValidarCodigoResponse {
        return client.post("${baseUrl}validar-codigo") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    suspend fun atualizarSenha(request: AtualizarSenhaRequest): AtualizarSenhaResponse {
        return client.post("${baseUrl}redefinir-senha") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
