package com.example.formacionkotlin.network

import com.example.formacionkotlin.models.CarItemResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val URL_GET_LAMBORGHINIS = "/get/LamborghiniModels"

internal interface ApiClient {

    companion object {
        private val _url = "https://cma-sas-everis.herokuapp.com"

        val service: ApiClient by lazy {
            Retrofit.Builder()
                .baseUrl(_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiClient::class.java)
        }
    }

}
