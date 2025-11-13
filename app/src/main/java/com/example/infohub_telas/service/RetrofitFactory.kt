package com.example.infohub_telas.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"

    // Configuração do logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("API_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY // Loga headers e body completo
    }

    // Interceptor para adicionar Content-Type: application/json
    private val jsonHeaderInterceptor = okhttp3.Interceptor { chain ->
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            .removeHeader("Content-Type") // Remove header existente se houver
            .addHeader("Content-Type", "application/json; charset=utf-8") // Adiciona com charset
            .addHeader("Accept", "application/json")
            .build()

        Log.d("API_HEADERS", "🔧 Request URL: ${request.url}")
        Log.d("API_HEADERS", "🔧 Method: ${request.method}")
        Log.d("API_HEADERS", "🔧 Content-Type: ${request.header("Content-Type")}")
        Log.d("API_HEADERS", "🔧 Accept: ${request.header("Accept")}")
        Log.d("API_HEADERS", "🔧 Authorization: ${request.header("Authorization")?.take(30)}...")

        chain.proceed(request)
    }

    // Cliente OkHttp com interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(jsonHeaderInterceptor) // Adiciona headers JSON
        .addInterceptor(loggingInterceptor)    // Loga requisições
        .build()

    private val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
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

    fun getRetrofit(): Retrofit {
        return retrofitFactory
    }

    // TODO: Adicionar outros services quando as interfaces forem criadas
    // fun getChatIAApiService(): ChatIAApiService {
    //     return retrofitFactory.create(ChatIAApiService::class.java)
    // }

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

    // Função comentada - pode ser descomentada quando necessário
    // fun getBrasilApiService(): BrasilApi {
    //     return brasilApiRetrofit().create(BrasilApi::class.java)
    // }
}
