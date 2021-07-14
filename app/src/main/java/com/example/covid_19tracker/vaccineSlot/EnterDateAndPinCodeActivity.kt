package com.example.covid_19tracker.vaccineSlot

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19tracker.databinding.ActivityEnterDateAndPincodeBinding
import java.util.*


class EnterDateAndPinCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterDateAndPincodeBinding

    private var postalCode: String = ""
    private var date: String = ""
    private var mYear: Int? = null
    private var mMonth: Int? = null
    private var mDay: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterDateAndPincodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibCalender.setOnClickListener {

            closeKeyboard()

            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.etDate.setText(dayOfMonth.toString() + "-0" + (+monthOfYear + 1) + "-" + year)
                },
                mYear!!,
                mMonth!!,
                mDay!!
            )
            datePickerDialog.show()
        }

        binding.searchButton.setOnClickListener {

            closeKeyboard()

            binding.progressBar.visibility = View.VISIBLE

            postalCode = binding.etPostalAddress.text.toString()
            date = binding.etDate.text.toString()

            var isOk = true

            if (postalCode.length != 6) {
                Toast.makeText(this, "Invalid PostalCode", Toast.LENGTH_SHORT).show()
                isOk = false
            }
            if (date.length != 10) {
                if (date[2] != '-') {
                    date = "0$date"
                } else {
                    isOk = false;
                    Toast.makeText(this, "Invalid date!!", Toast.LENGTH_SHORT).show()
                }
            }
            if (isOk) {

                binding.progressBar.visibility = View.GONE

                val intent = Intent(this, VaccineSlots::class.java)
                intent.putExtra("postal_code", postalCode)
                intent.putExtra("date", date)
                startActivity(intent)

            }
        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

}
