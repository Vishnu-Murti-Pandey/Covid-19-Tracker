package com.example.covid_19tracker.getHelp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19tracker.databinding.ActivityHelpLineNumbersBinding


class HelpLineNumbersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpLineNumbersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpLineNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.healthMinistry.setOnClickListener {
            openHealthMinistryCard()
        }
        binding.childHelp.setOnClickListener {
            openChildHelpCard()
        }
        binding.mentalHelp.setOnClickListener {
            openMentalHelpCard()
        }
        binding.seniorHelp.setOnClickListener {
            openSeniorCitizenCard()
        }
        binding.ayushMinistry.setOnClickListener {
            opneAyushMinistryCard()
        }
        binding.whatsappHelp.setOnClickListener {
            openWhatsappCard()
        }
        binding.emailHelp.setOnClickListener {
            openEmailCard()
        }

    }

    private fun openEmailCard() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi, this is Vishnu...")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun openWhatsappCard() {

        val number = "+919013151515"
        val url = "https://api.whatsapp.com/send/?phone=$number&text=Hi, this is Vishnu&app_absent=0"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun opneAyushMinistryCard() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:14443")
        startActivity(intent)
    }

    private fun openSeniorCitizenCard() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:14567")
        startActivity(intent)
    }

    private fun openMentalHelpCard() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:08046110007")
        startActivity(intent)
    }

    private fun openChildHelpCard() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:1098")
        startActivity(intent)
    }

    private fun openHealthMinistryCard() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:1075")
        startActivity(intent)
    }
}