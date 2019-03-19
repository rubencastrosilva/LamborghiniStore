package com.example.formacionkotlin.network

import com.example.formacionkotlin.models.CarItemResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


const val URL_GET_LAMBORGHINIS = "/get/LamborghiniModels"

internal interface ApiClient {

    // Recordamos que un companion opject es un objeto que estará siempre instanciado en memoria.
    // Con lo cual, no tendremos que inicializarlo para acceder a sus valores, si hicieramos ApiCLlient.service
    // funcionaría sin problemas.

    companion object {
        //URL base
        private val _url = "https://cma-sas-everis.herokuapp.com"

        val service: ApiClient by lazy {
            Retrofit.Builder()
                .baseUrl(_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiClient::class.java)
        }
    }
    @GET(URL_GET_LAMBORGHINIS)
    fun getLamborghinis(): Call<CarItemResponse>
}
