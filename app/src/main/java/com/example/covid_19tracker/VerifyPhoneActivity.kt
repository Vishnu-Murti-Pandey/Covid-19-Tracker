package com.example.covid_19tracker

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19tracker.databinding.ActivityVerifyPhoneBinding
import com.google.firebase.auth.*

class VerifyPhoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyPhoneBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runCountdown()

        val bundle: Bundle? = intent.extras
        val mVerificationId: String? = bundle?.getString("storedVerificationId")

        auth = FirebaseAuth.getInstance()


        binding.btSignIn.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE

            val code = binding.etVerificationCode.text.toString().trim()

            if (code.isEmpty() || code.length != 6) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Enter valid verification code...", Toast.LENGTH_SHORT).show()
            } else {
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(mVerificationId.toString(), code)
                signInWithPhoneAuthCredential(credential)
            }
        }

    }

    private fun runCountdown() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvClock.text = ""+ millisUntilFinished / 1000
            }
            override fun onFinish() {
                binding.tvClock.text = "Retry!!"
            }
        }.start()
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.progressBar.visibility = View.GONE

                    binding.verified.visibility = View.VISIBLE

                    Toast.makeText(applicationContext, "Log In Successful", Toast.LENGTH_LONG)
                        .show()

                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "The verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Update UI
                }
            }
    }


}