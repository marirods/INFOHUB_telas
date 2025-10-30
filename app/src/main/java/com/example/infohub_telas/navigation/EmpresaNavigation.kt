package com.example.infohub_telas.navigation

sealed class EmpresaScreens(val route: String) {
    object ListaEmpresas : EmpresaScreens("lista_empresas")
    object CadastroEmpresa : EmpresaScreens("cadastro_empresa")
    object DetalhesEmpresa : EmpresaScreens("detalhes_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "detalhes_empresa/$empresaId"
    }
    object EditarEmpresa : EmpresaScreens("editar_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "editar_empresa/$empresaId"
    }
    object DocumentosEmpresa : EmpresaScreens("documentos_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "documentos_empresa/$empresaId"
    }
    object RelatoriosEmpresa : EmpresaScreens("relatorios_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "relatorios_empresa/$empresaId"
    }
}
