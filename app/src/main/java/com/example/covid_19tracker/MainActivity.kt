package com.example.covid_19tracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.covid_19tracker.databinding.ActivityMainBinding
import com.example.covid_19tracker.getHelp.GetHelpActivity
import com.example.covid_19tracker.retrofitApi.CountryData
import com.example.covid_19tracker.retrofitApi.CountryService
import com.example.covid_19tracker.statesUpdate.StatesActivity
import com.example.covid_19tracker.trackCountries.SelectCountry
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    var countryList = ArrayList<CountryData>()

    var currCountry: String = "India"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.spinner.setOnClickListener {
            val intent = Intent(this, SelectCountry::class.java)
            startActivity(intent)
        }

        if (intent.getStringExtra("country") != null) {
            currCountry = intent.getStringExtra("country")!!
        }

        binding.spinner.text = currCountry

        countryList = ArrayList()

        getCountry()

        binding.btGetHelp.setOnClickListener {
            val intent = Intent(this, GetHelpActivity::class.java)
            startActivity(intent)
        }

        binding.btStatesUpdates.setOnClickListener {
            val intent = Intent(this, StatesActivity::class.java)
            startActivity(intent)
        }

    }


    private fun getCountry() {

        val country: Call<List<CountryData>> = CountryService.countryInstance.getCountryData()
        country.enqueue(object : Callback<List<CountryData>> {
            override fun onResponse(
                call: Call<List<CountryData>>,
                response: Response<List<CountryData>>
            ) {
                response.body()?.let {
                    countryList.addAll(it)
                }

                for (i in 0 until countryList.size) {
                    if (countryList[i].country == currCountry) {

                        binding.updatedAt.text = updatedAt(countryList[i].updated)

                        binding.totalConfirm.text = NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].cases))
                        binding.totalActive.text = NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].active))
                        binding.totalRecovered.text = NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].recovered))
                        binding.totalDeath.text = NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].deaths))

                        ("+" + NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].todayCases))).also {
                            binding.totalConfirmToday.text = it
                        }
                        ("+" + NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].todayRecovered))).also {
                            binding.totalRecoveredToday.text = it
                        }
                        ("+" + NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].todayDeaths))).also {
                            binding.totalDeathToday.text = it
                        }

                        binding.totalTest.text = NumberFormat.getInstance()
                            .format(Integer.parseInt(countryList[i].tests))

                        binding.pieChart.addPieSlice(
                            PieModel(
                                "Confirm",
                                Integer.parseInt(countryList[i].cases).toFloat(),
                                Color.parseColor("#FFFF00")
                            )
                        )

                        binding.pieChart.addPieSlice(
                            PieModel(
                                "Active",
                                Integer.parseInt(countryList[i].active).toFloat(),
                                Color.parseColor("#D500F9")
                            )
                        )

                        binding.pieChart.addPieSlice(
                            PieModel(
                                "Recovered",
                                Integer.parseInt(countryList[i].recovered).toFloat(),
                                Color.parseColor("#76FF03")
                            )
                        )

                        binding.pieChart.addPieSlice(
                            PieModel(
                                "Death",
                                Integer.parseInt(countryList[i].deaths).toFloat(),
                                Color.parseColor("#D50000")
                            )
                        )

                        binding.pieChart.startAnimation()

                    }
                }

            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                Toast.makeText(applicationContext, "Something Went Wrong!!!", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

    private fun updatedAt(updated: String): String {

        val format = SimpleDateFormat("MMM dd, yyyy")

        val milliSeconds = updated.toLong()

        val calender = Calendar.getInstance()
        calender.timeInMillis = milliSeconds

        return "Updated At " + format.format(calender.time)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle item selection
        return when (item.itemId) {
            R.id.log_out_menu -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        auth.signOut()

        Toast.makeText(this, "Log Out Successful", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, MobileNumberEnterActivity::class.java))
        finish()
    }

}
