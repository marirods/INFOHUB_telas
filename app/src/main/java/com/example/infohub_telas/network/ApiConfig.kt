package com.example.infohub_telas.network

import android.content.Context
import com.example.infohub_telas.network.interceptor.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuração centralizada da API do InfoHub
 * Base URL: http://localhost:8080/v1/infohub
 */
class ApiConfig(private val context: Context) {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"

        @Volatile
        private var INSTANCE: ApiConfig? = null

        fun getInstance(context: Context): ApiConfig {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiConfig(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // Logging interceptor para debug
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Auth interceptor para JWT
    private val authInterceptor = AuthInterceptor(context)

    // Interceptor para garantir Content-Type JSON
    private val jsonInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Accept", "application/json")
            .build()

        chain.proceed(newRequest)
    }

    // Cliente HTTP com configurações customizadas
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(jsonInterceptor) // Content-Type JSON
        .addInterceptor(authInterceptor) // Interceptor de autenticação
        .addInterceptor(loggingInterceptor) // Logging
        .build()

    // Configuração customizada do Gson
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    // Instância do Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // Factory methods para criar services
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    // Services específicos - todos os serviços da API
    val authService: AuthService by lazy { createService(AuthService::class.java) }
    val userService: UserService by lazy { createService(UserService::class.java) }
    val estabelecimentoService: EstabelecimentoService by lazy { createService(EstabelecimentoService::class.java) }
    val enderecoService: EnderecoService by lazy { createService(EnderecoService::class.java) }
    val categoriaService: CategoriaService by lazy { createService(CategoriaService::class.java) }
    val produtoService: ProdutoService by lazy { createService(ProdutoService::class.java) }
    val carrinhoService: CarrinhoService by lazy { createService(CarrinhoService::class.java) }
    val pedidoService: PedidoService by lazy { createService(PedidoService::class.java) }
    val promocaoService: PromocaoService by lazy { createService(PromocaoService::class.java) }
    val postService: PostService by lazy { createService(PostService::class.java) }
    val avaliacaoService: AvaliacaoService by lazy { createService(AvaliacaoService::class.java) }
    val favoritoService: FavoritoService by lazy { createService(FavoritoService::class.java) }
    val notificacaoService: NotificacaoService by lazy { createService(NotificacaoService::class.java) }
    val chatIAService: ChatIAService by lazy { createService(ChatIAService::class.java) }
    val infoCashService: InfoCashService by lazy { createService(InfoCashService::class.java) }
}
