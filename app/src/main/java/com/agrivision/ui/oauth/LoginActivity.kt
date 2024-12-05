package com.agrivision.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agrivision.MainActivity
import com.agrivision.data.companion.LoginRequest
import com.agrivision.data.local.DataStoreManager
import com.agrivision.data.remote.response.LoginResponse
import com.agrivision.data.remote.retrofit.ApiConfig
import com.agrivision.databinding.ActivityLoginBinding
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
                loginUser(emailOrUsername, password)
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
        binding.linkToRegister.setOnClickListener{onRegisterLinkClick()}
    }

    fun onRegisterLinkClick() {
        // Navigate to RegisterActivity
        val intent = Intent(
            this@LoginActivity,
            RegisterActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun loginUser(emailOrUsername: String, password: String) {
        val dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch {
            try {
                // Perform the suspend function directly in the coroutine scope
                val response: Response<LoginResponse> = ApiConfig.getApiService().loginUser(
                    LoginRequest(emailOrUsername, password)
                )

                if (response.isSuccessful) {
                    val username = emailOrUsername
                    dataStoreManager.saveUsername(username)
                    Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.putExtra("EXTRA_USERNAME", username)
                    startActivity(intent)
                    finish()
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
