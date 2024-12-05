package com.agrivision.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agrivision.data.companion.RegisterRequest
import com.agrivision.data.remote.retrofit.ApiConfig
import com.agrivision.databinding.ActivityRegisterBinding
import com.google.gson.JsonParser
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {

    // Declare the view binding object
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view binding object
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Now you can safely access the views via the binding object
        val linkToRegister = binding.linkToLogin
        linkToRegister.setOnClickListener {
            // Logic to navigate to RegisterActivity or perform the intended action
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set onClickListener for the register button using view binding
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val nickname = binding.etNickname.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty()) {
                // Start a coroutine to handle the registration
                registerUser(email, username, password, nickname)
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, username: String, password: String, nickname: String) {
        // Use CoroutineScope for network calls
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val registerRequest = RegisterRequest(email, username, password, nickname)

                val response = apiService.registerUser(registerRequest)

                if (response.isSuccessful) {
//                    Toast.makeText(this@RegisterActivity, "Registrasi berhasil! Silakan verifikasi email Anda.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, VerifyEmailActivity::class.java)
                    intent.putExtra("EXTRA_USERNAME",username)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody ()?.string()
                    errorBody?.let {
                        try {
                            val jsonObject =  JsonParser().parse(it).asJsonObject
                            val errorMessage = jsonObject.get("error").asString
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registrasi gagal: $errorMessage",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Terjadi kesalahan: Registrasi gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show()
                Log.e("fail register","Terjadi kesalahan: ${e.message}")
            }
        }
    }
}
