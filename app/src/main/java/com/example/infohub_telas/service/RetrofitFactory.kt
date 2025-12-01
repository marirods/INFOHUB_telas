package com.example.infohub_telas.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"

    // Configuração do logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor { message: String ->
        Log.d("API_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY // Loga headers e body completo
    }

    // Interceptor para garantir Content-Type: application/json
    private val jsonHeaderInterceptor = okhttp3.Interceptor { chain ->
        val originalRequest = chain.request()

        // Verifica se já tem Content-Type
        val hasContentType = originalRequest.header("Content-Type") != null

        val requestBuilder = originalRequest.newBuilder()

        // Só adiciona Content-Type se não existir OU se o método tiver body (POST, PUT, PATCH)
        if (!hasContentType && originalRequest.body != null) {
            requestBuilder.addHeader("Content-Type", "application/json; charset=utf-8")
        }

        // Sempre adiciona Accept
        requestBuilder.addHeader("Accept", "application/json")

        val request = requestBuilder.build()

        Log.d("API_HEADERS", "🔧 Request URL: ${request.url}")
        Log.d("API_HEADERS", "🔧 Method: ${request.method}")
        Log.d("API_HEADERS", "🔧 Content-Type: ${request.header("Content-Type") ?: "NÃO DEFINIDO"}")
        Log.d("API_HEADERS", "🔧 Accept: ${request.header("Accept")}")
        Log.d("API_HEADERS", "🔧 Authorization: ${request.header("Authorization")?.take(30) ?: "SEM TOKEN"}...")
        Log.d("API_HEADERS", "🔧 Has Body: ${request.body != null}")

        chain.proceed(request)
    }

    // Cliente OkHttp com interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(jsonHeaderInterceptor) // Adiciona headers JSON
        .addInterceptor(loggingInterceptor)    // Loga requisições
        .build()

    // Configuração do Gson com configurações personalizadas
    private val gson = com.google.gson.GsonBuilder()
        .setLenient() // Permite JSON menos rígido
        .serializeNulls() // Serializa campos null
        .create()

    private val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun getInfoHub_UserService(): InfoHub_User {
        return retrofitFactory.create(InfoHub_User::class.java)
    }

    fun getInfoHub_EstabelecimentoService(): InfoHub_EstabelecimentoService {
        return retrofitFactory.create(InfoHub_EstabelecimentoService::class.java)
    }

    fun getInfoHub_ProdutoService(): InfoHub_ProdutoService {
        return retrofitFactory.create(InfoHub_ProdutoService::class.java)
    }

    fun getInfoCashApiService(): InfoCashApiService {
        return retrofitFactory.create(InfoCashApiService::class.java)
    }

    fun getCarrinhoApiService(): CarrinhoApiService {
        return retrofitFactory.create(CarrinhoApiService::class.java)
    }

    fun getAvaliacaoApiService(): AvaliacaoApiService {
        return retrofitFactory.create(AvaliacaoApiService::class.java)
    }

    fun getCategoriaApiService(): CategoriaApiService {
        return retrofitFactory.create(CategoriaApiService::class.java)
    }

    /**
     * Serviço para API de Produtos com formato correto da documentação
     * Usa suspend functions e Response<ApiResponse<Produto>>
     */
    fun getProdutoApiService(): ProdutoApiService {
        return retrofitFactory.create(ProdutoApiService::class.java)
    }

    /**
     * Serviço para API de Carrinho V2 - CONFORME DOCUMENTAÇÃO OFICIAL
     * Usa formato correto: POST /carrinho com body { id_usuario, id_produto, quantidade }
     */
    fun getCarrinhoApiServiceV2(): CarrinhoApiServiceV2 {
        return retrofitFactory.create(CarrinhoApiServiceV2::class.java)
    }

    /**
     * Serviço para API de Estabelecimentos - CONFORME DOCUMENTAÇÃO OFICIAL
     * GET /estabelecimentos, /estabelecimento/{id}, etc.
     */
    fun getEstabelecimentoApiService(): EstabelecimentoApiService {
        return retrofitFactory.create(EstabelecimentoApiService::class.java)
    }

    fun getRetrofit(): Retrofit {
        return retrofitFactory
    }

    private fun viaCepRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getViaCepService(): ViaCepApi {
        return viaCepRetrofit().create(ViaCepApi::class.java)
    }

    private fun brasilApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://brasilapi.com.br/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
