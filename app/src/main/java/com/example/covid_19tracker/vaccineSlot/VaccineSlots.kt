package com.example.covid_19tracker.vaccineSlot

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.covid_19tracker.databinding.VaccineSlotsBinding

class VaccineSlots : AppCompatActivity(), VaccineItemClicked {

    private lateinit var binding: VaccineSlotsBinding
    private lateinit var mAdapter: VaccineSlotAdapter
    private var vaccineURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineSlotsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val postalCode: String? = bundle?.getString("postal_code")
        val date: String? = bundle?.getString("date")

        vaccineURL =
            "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=$postalCode&date=$date"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = VaccineSlotAdapter(this)

        binding.recyclerView.adapter = mAdapter

    }

    private fun fetchData() {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, vaccineURL, null,
            {
                val vaccineCenterJSONArray = it.getJSONArray("centers")

                val vaccineArray = ArrayList<Vaccine>()

                for (j in 0 until vaccineCenterJSONArray.length()) {
                    val vaccineJsonObject = vaccineCenterJSONArray.getJSONObject(j)
                    val vaccineSessionJSONArray = vaccineJsonObject.getJSONArray("sessions")

                    for (i in 0 until vaccineSessionJSONArray.length()) {

                        val vaccineSessionJSONObject = vaccineSessionJSONArray.getJSONObject(i)

                        val vaccine = Vaccine(
                            vaccineJsonObject.getString("name"),
                            vaccineJsonObject.getString("address"),
                            vaccineJsonObject.getString("from"),
                            vaccineJsonObject.getString("to"),
                            vaccineJsonObject.getString("fee_type"),

                            vaccineSessionJSONObject.getString("available_capacity"),
                            vaccineSessionJSONObject.getString("min_age_limit"),
                            vaccineSessionJSONObject.getString("vaccine")
                        )
                        vaccineArray.add(vaccine)
                    }
                }

                mAdapter.updateNews(vaccineArray)

            },
            {
                Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Vaccine) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.cowin.gov.in/home"));
    }

}