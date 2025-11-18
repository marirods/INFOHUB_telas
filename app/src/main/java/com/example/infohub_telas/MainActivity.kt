package com.example.infohub_telas

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.navigation.AppNavigation
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    // Função para login com Google
    suspend fun signInWithGoogle(
        onSuccess: (userId: String, displayName: String?, email: String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val credentialManager = CredentialManager.create(this)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("SEU_WEB_CLIENT_ID_AQUI") // ← Coloque seu Web Client ID aqui
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = this,
            )

            val credential = result.credential

            if (credential is GoogleIdTokenCredential) {
                val idToken = credential.idToken
                val userId = credential.id
                val displayName = credential.displayName

                // Callback de sucesso
                onSuccess(userId, displayName, userId)
            }
        } catch (e: Exception) {
            onError(e.message ?: "Erro desconhecido")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize OSMdroid in background thread to avoid blocking main thread
        Thread {
            try {
                Configuration.getInstance().load(
                    applicationContext,
                    PreferenceManager.getDefaultSharedPreferences(applicationContext)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        setContent {
            InfoHub_telasTheme {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                // Usando o AppNavigation centralizado
                AppNavigation(
                    navController = navController,
                    onGoogleSignIn = {
                        scope.launch {
                            signInWithGoogle(
                                onSuccess = { userId, displayName, email ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Bem-vindo, $displayName!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    // Navega para a home após login
                                    navController.navigate("home") {
                                        popUpTo("login_cadastro") { inclusive = true }
                                    }
                                },
                                onError = { errorMessage ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Erro: $errorMessage",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }
                    },
                    onAppleSignIn = {
                        Toast.makeText(
                            this@MainActivity,
                            "Apple Sign-In ainda não implementado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}