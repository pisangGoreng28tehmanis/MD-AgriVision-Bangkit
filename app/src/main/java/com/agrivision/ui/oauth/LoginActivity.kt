package com.agrivision.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agrivision.databinding.ActivityLoginBinding
import com.example.agrivision.api.ConfigApi
import com.example.agrivision.api.LoginRequest
import com.example.agrivision.api.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val emailOrUsername = binding.etEmailOrUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (emailOrUsername.isNotEmpty() && password.isNotEmpty()) {
                // Call login function with coroutine
                loginUser(emailOrUsername, password)
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onRegisterLinkClick(view: View?) {
        // Navigate to RegisterActivity
        val intent = Intent(
            this@LoginActivity,
            RegisterActivity::class.java
        )
        startActivity(intent)
    }

    private fun loginUser(emailOrUsername: String, password: String) {
        // Launch a coroutine in lifecycleScope
        lifecycleScope.launch {
            try {
                // Perform the suspend function directly in the coroutine scope
                val response: Response<LoginResponse> = ConfigApi.apiService.loginUser(LoginRequest(emailOrUsername, password))

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    // Navigate to the next screen or store the token
                } else {
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle network errors
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
