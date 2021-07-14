package com.example.covid_19tracker.retrofitApi

data class CountryData(

    val updated: String,
    val country: String,
    val cases: String,
    val todayCases: String,
    val deaths: String,
    val todayDeaths: String,
    val recovered: String,
    val todayRecovered: String,
    val active: String,
    val tests: String,
    val countryInfo: Map<String, String>

)
