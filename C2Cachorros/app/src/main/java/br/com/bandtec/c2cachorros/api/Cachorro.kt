package br.com.bandtec.c2cachorros.api

data class Cachorro(
    var id: Int,
    var raca: String,
    var precoMedio: Int,
    var indicadoCriancas: Boolean
)
