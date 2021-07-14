package com.example.covid_19tracker.getHelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid_19tracker.R
import com.example.covid_19tracker.databinding.ActivityCovidSymptomsBinding

class CovidSymptomsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCovidSymptomsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCovidSymptomsBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}