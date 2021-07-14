package com.example.covid_19tracker.getHelp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import com.example.covid_19tracker.R
import com.example.covid_19tracker.databinding.ActivityGetHelpBinding
import com.example.covid_19tracker.vaccineSlot.EnterDateAndPinCodeActivity

class GetHelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.vaccineAvailableDetails.setOnClickListener {
            val intent = Intent(this, EnterDateAndPinCodeActivity::class.java)
            startActivity(intent)
        }

        binding.bookSlot.setOnClickListener {
            openCowinWebsiteCard()
        }

        binding.helpRegardingVaccine.setOnClickListener {
            val intent = Intent(this, HelpRegardingVaccineActivity::class.java)
            startActivity(intent)
        }

        binding.hospitalBeds.setOnClickListener {
            showDialog()
        }

        binding.covidSymptoms.setOnClickListener {
            val intent = Intent(this, CovidSymptomsActivity::class.java)
            startActivity(intent)
        }

        binding.help.setOnClickListener {
            val intent = Intent(this, DonateActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.activity_dialog, null)

        with(builder) {
            setPositiveButton("OK") { dialog, which ->
                openBedsAvailabilityCard()
            }
            setNegativeButton("Cancel") { dialog, which ->
                Log.d("Main", "Negative Button Clicked")
            }
            setView(dialogLayout)
            show()
        }
    }

    private fun openBedsAvailabilityCard() {

        val url = "https://static.mygov.in/rest/s3fs-public/mygov_162159261551307401.pdf"

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    private fun openCowinWebsiteCard() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.cowin.gov.in/"));
    }
}