package br.com.bandtec.c2cachorros.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCachorros {

    @GET("cachorros/{id}")
    fun get(@Path("id") id: Int): Call<Cachorro>
}