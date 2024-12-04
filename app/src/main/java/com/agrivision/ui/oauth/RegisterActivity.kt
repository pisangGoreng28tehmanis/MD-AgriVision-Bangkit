package com.agrivision.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agrivision.R
import com.agrivision.databinding.ActivityRegisterBinding
import com.example.agrivision.api.ConfigApi
import com.example.agrivision.api.RegisterRequest
import com.example.agrivision.api.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = ConfigApi.apiService
                val registerRequest = RegisterRequest(email, username, password, nickname)

                // Call the suspend function from the apiService
                val response = apiService.registerUser(registerRequest)

                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registrasi berhasil! Silakan verifikasi email Anda.", Toast.LENGTH_SHORT).show()
                    // Navigate to email verification screen
                } else {
                    Log.e("RegisterError", "Error Code: ${response.code()} Message: ${response.message()}")
                    Toast.makeText(this@RegisterActivity, "Registrasi gagal: ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
