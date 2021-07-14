package com.example.covid_19tracker.retrofitApi

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://disease.sh/v2/"

interface CountryInterface {

    @GET("countries")
    fun getCountryData(): Call<List<CountryData>>

}

object CountryService {

    val countryInstance: CountryInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        countryInstance = retrofit.create(CountryInterface::class.java)
    }

}
