package com.example.infohub_telas.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text  // Adicionado para mensagens de erro (opcional)
import androidx.compose.ui.graphics.Color

// Data class para encapsular os dados do formulário
data class FormPessoaFisicaData(
    val nome: String = "",
    val cpf: String = "",
    val telefone: String = "",
    val email: String = "",
    val senha: String = "",
    val confirmarSenha: String = "",
    val mostrarSenha: Boolean = false,
    val mostrarConfirmarSenha: Boolean = false
)

@Composable
fun FormPessoaFisica(
    data: FormPessoaFisicaData,
    onDataChange: (FormPessoaFisicaData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CustomTextFieldCadastro(
            value = data.nome,
            onValueChange = { onDataChange(data.copy(nome = it)) },
            placeholder = "Nome Completo*"
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = data.cpf,
            onValueChange = { onDataChange(data.copy(cpf = it)) },
            placeholder = "CPF*",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = data.telefone,
            onValueChange = { onDataChange(data.copy(telefone = it)) },
            placeholder = "Telefone*",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = data.email,
            onValueChange = { onDataChange(data.copy(email = it)) },
            placeholder = "E-mail*",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        SenhaTextFieldCadastro(
            value = data.senha,
            onValueChange = { onDataChange(data.copy(senha = it)) },
            mostrarSenha = data.mostrarSenha,
            onMostrarSenhaChange = { onDataChange(data.copy(mostrarSenha = it)) },
            placeholder = "Senha*"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SenhaTextFieldCadastro(
            value = data.confirmarSenha,
            onValueChange = { onDataChange(data.copy(confirmarSenha = it)) },
            mostrarSenha = data.mostrarConfirmarSenha,
            onMostrarSenhaChange = { onDataChange(data.copy(mostrarConfirmarSenha = it)) },
            placeholder = "Confirme a senha*"
        )

        // Validação simples: Exemplo de erro se senhas não coincidirem
        if (data.senha.isNotEmpty() && data.confirmarSenha.isNotEmpty() && data.senha != data.confirmarSenha) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "As senhas não coincidem",
                color = Color.Red
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewFormPessoaFisica() {
    FormPessoaFisica(
        data = FormPessoaFisicaData(),
        onDataChange = {}
    )
}


