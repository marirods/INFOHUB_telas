package com.example.infohub_telas.service

import com.example.infohub_telas.model.Cidade
import retrofit2.http.GET
import retrofit2.http.Path

interface BrasilApi {
        @GET("ibge/municipios/v1/{uf}")
        suspend fun buscarCidadesPorUF(@Path("uf") uf: String): List<Cidade>

}