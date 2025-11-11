package com.example.infohub_telas.service

import com.example.infohub_telas.model.CepResponse
import com.example.infohub_telas.model.Cidade
import retrofit2.http.GET
import retrofit2.http.Path

interface BrasilApi {

        // 🔹 Endpoint existente (mantém igual)
        @GET("ibge/municipios/v1/{uf}")
        suspend fun buscarCidadesPorUF(@Path("uf") uf: String): List<Cidade>

        // 🔹 Novo endpoint (CEP → coordenadas)
        @GET("cep/v2/{cep}")
        suspend fun getCep(@Path("cep") cep: String): CepResponse
}
