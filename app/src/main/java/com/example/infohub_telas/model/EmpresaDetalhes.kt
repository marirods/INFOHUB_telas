package com.example.infohub_telas.model

data class EmpresaDetalhes(
    val id: String,
    val nome: String,
    val cnpj: String,
    val status: CompanyStatus,
    val dataAbertura: String,
    val responsavel: String,
    val endereco: String,
    val telefone: String,
    val email: String,
    val documentos: List<String> = emptyList(),
    val relatorios: List<String> = emptyList()
)
