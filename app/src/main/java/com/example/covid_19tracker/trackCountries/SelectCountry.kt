package com.example.covid_19tracker.trackCountries

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19tracker.MainActivity
import com.example.covid_19tracker.databinding.ActivitySelectCountryBinding
import com.example.covid_19tracker.retrofitApi.CountryData
import com.example.covid_19tracker.retrofitApi.CountryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SelectCountry : AppCompatActivity(), CountryItemClicked {

    private lateinit var binding: ActivitySelectCountryBinding
    private lateinit var mAdapter: CountryAdapter

    var countryList = ArrayList<CountryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryList = ArrayList()

        binding.countryProgressBar.visibility = View.VISIBLE

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()

        mAdapter = CountryAdapter(this)

        binding.recyclerView.adapter = mAdapter

    }

    private fun fetchData() {

        val country: Call<List<CountryData>> = CountryService.countryInstance.getCountryData()
        country.enqueue(object : Callback<List<CountryData>> {
            override fun onResponse(
                call: Call<List<CountryData>>,
                response: Response<List<CountryData>>
            ) {
                response.body()?.let {
                    countryList.addAll(it)
                }

                mAdapter.updateCountry(countryList)
                binding.countryProgressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                binding.countryProgressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Error Occurred!!!", Toast.LENGTH_LONG).show()
            }

        })

        binding.searchBar.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }


        })

    }

    private fun filter(text: String) {

        val filterList: ArrayList<CountryData> = ArrayList()

        for (items: CountryData in countryList) {
            if (items.country.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filterList.add(items)
            }
        }

        mAdapter.filterCountry(filterList)
    }

    override fun onItemClicked(item: CountryData) {

        closeKeyboard()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("country", item.country)
        startActivity(intent)

    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

}

