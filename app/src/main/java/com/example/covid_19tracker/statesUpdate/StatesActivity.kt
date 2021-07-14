package com.example.covid_19tracker.statesUpdate

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.covid_19tracker.databinding.ActivityStatesBinding
import com.example.covid_19tracker.vaccineSlot.VolleySingleton
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class StatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatesBinding
    private lateinit var mAdapter: StateAdapter
    private var stateURL = "https://api.covid19india.org/data.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.statesRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()

        mAdapter = StateAdapter()
        binding.statesRecyclerView.adapter = mAdapter

    }

    private fun fetchData() {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, stateURL, null,
            {
                val statesJSONArray = it.getJSONArray("statewise")

                val stateArray = ArrayList<StatesData>()

                for (i in 0 until statesJSONArray.length()) {
                    val stateJsonObject = statesJSONArray.getJSONObject(i)

                    val stateData = StatesData(
                        stateJsonObject.getString("active"),
                        stateJsonObject.getString("confirmed"),
                        stateJsonObject.getString("deaths"),
                        stateJsonObject.getString("lastupdatedtime"),
                        stateJsonObject.getString("recovered"),
                        stateJsonObject.getString("state")
                    )
                    if (i == 0) {
                        binding.confirmedTv.text = stateData.confirm
                        binding.activeTv.text = stateData.active
                        binding.recoveredTv.text = stateData.recovered
                        binding.deceasedTv.text = stateData.deaths
                    }

                    val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    binding.lastUpdatedTv.text = "Last Updated\n ${
                        lastUpdateTime(
                            date.parse(stateData.lastUpdatedTime)
                        )
                    }"

                    if (i == 0) {
                        continue
                    } else {
                        stateArray.add(stateData)
                    }
                }

                mAdapter.updateCountry(stateArray)

            },
            {
                Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun lastUpdateTime(lastUpdatedTime: Date): CharSequence {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - lastUpdatedTime.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - lastUpdatedTime.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - lastUpdatedTime.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes min ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(lastUpdatedTime).toString()
            }
        }
    }

}