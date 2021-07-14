package com.example.covid_19tracker.getHelp

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.covid_19tracker.databinding.ActivityHelpRegardingVaccineBinding
import com.example.covid_19tracker.newsBlog.NewsActivity
import com.google.android.gms.common.internal.Constants


class HelpRegardingVaccineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpRegardingVaccineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpRegardingVaccineBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.expertAdvice.setOnClickListener {
            openYouTubeExpertAdvice()
        }

        binding.indiaGovWebsite.setOnClickListener {
            openGovIndiaWebsite()
        }
        binding.helpLineNumbers.setOnClickListener {
            val intent = Intent(this, HelpLineNumbersActivity::class.java)
            startActivity(intent)
        }

        binding.blogNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        binding.travelGuidelines.setOnClickListener {
            openPdfView()
        }

        binding.faq.setOnClickListener {
            openFaq()
        }

    }

    private fun openFaq() {

        val url = "https://www.mohfw.gov.in/covid_vaccination/vaccination/faqs.html"

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private fun openPdfView() {

        val url = "https://static.mygov.in/rest/s3fs-public/mygov_162398954051307401.pdf"

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    private fun openGovIndiaWebsite() {

        val url = "https://www.mygov.in/covid-19/"

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private fun openYouTubeExpertAdvice() {

        val url = "https://www.youtube.com/c/YouTubeIndia/featured"

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }
}