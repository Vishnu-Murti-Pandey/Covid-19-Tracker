package com.example.covid_19tracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19tracker.databinding.ActivityMobileNumberEnterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class MobileNumberEnterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMobileNumberEnterBinding

    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var mVerificationId: String
    private lateinit var auth: FirebaseAuth

    private lateinit var mobileNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileNumberEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btContinue.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            login()
        }

        // Callback function for Phone Auth
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                binding.progressBar.visibility = View.GONE
                startActivity(Intent(applicationContext, VerifyPhoneActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent:$verificationId")
                mVerificationId = verificationId
                forceResendingToken = token

                binding.progressBar.visibility = View.GONE
                val intent = Intent(applicationContext, VerifyPhoneActivity::class.java)
                intent.putExtra("storedVerificationId", mVerificationId)
                startActivity(intent)
            }
        }


    }

    private fun login() {
        mobileNumber = binding.etPhoneNumber.text.toString().trim()

        if (mobileNumber.length != 10) {
            Toast.makeText(this, "Enter valid mobile number!!", Toast.LENGTH_SHORT).show()
        } else {
            mobileNumber = "+91$mobileNumber"
            sendVerificationCode(mobileNumber)
        }
    }

    private fun sendVerificationCode(mobileNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobileNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

}