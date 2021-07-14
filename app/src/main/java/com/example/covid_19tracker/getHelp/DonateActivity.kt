package com.example.covid_19tracker.getHelp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import com.example.covid_19tracker.R
import com.example.covid_19tracker.databinding.ActivityDonateBinding

class DonateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ketto.setOnClickListener {
            openKetto()
        }

        binding.giveIndia.setOnClickListener {
            openGiveIndia()
        }

        binding.milaap.setOnClickListener {
            openMilaap()
        }

        binding.pmCare.setOnClickListener {
            openPMCare()
        }

        binding.unicef.setOnClickListener {
            openUNICEF()
        }

        binding.globalGiving.setOnClickListener {
            openGlobalGiving()
        }

    }

    private fun openGlobalGiving() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.globalgiving.org/projects/india-covid-19-relief-fund/"))
    }

    private fun openUNICEF() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.unicef.org/coronavirus/unicef-responding-covid-19-india"))
    }

    private fun openPMCare() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.pmcares.gov.in/en/"));
    }

    private fun openMilaap() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://milaap.org/"));
    }

    private fun openGiveIndia() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://covid.giveindia.org/"));
    }

    private fun openKetto() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://covid19.ketto.org/"));
    }
}