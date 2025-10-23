package com.example.infohub_telas.service

import com.example.infohub_telas.model.Estabelecimento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InfoHub_EstabelecimentoService {

    @POST("/estabelecimentos")
    fun cadastrarEstabelecimento(@Body estabelecimento: Estabelecimento): Call<Estabelecimento>
}
