package com.example.infohub_telas.model

data class Coordinates(
    val longitude: Double?,
    val latitude: Double?
)

data class Location(
    val type: String?,
    val coordinates: Coordinates?
)

data class CepResponse(
    val cep: String?,
    val state: String?,
    val city: String?,
    val neighborhood: String?,
    val street: String?,
    val service: String?,
    val location: Location?
)


