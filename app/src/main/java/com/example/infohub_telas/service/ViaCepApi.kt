package com.example.infohub_telas.service

import com.example.infohub_telas.model.EnderecoViaCep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {
        @GET("{cep}/json/")
        suspend fun buscarCep(@Path("cep") cep: String): Response<EnderecoViaCep>
}

