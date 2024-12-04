package com.agrivision.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agrivision.databinding.ActivityVerifyEmailBinding
import com.example.agrivision.api.ConfigApi
import com.example.agrivision.api.VerifyRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class VerifyEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVerify.setOnClickListener {
            val verificationCode = binding.etVerificationCode.text.toString()

            if (verificationCode.isNotEmpty()) {
                verifyEmailCode(verificationCode)
            } else {
                Toast.makeText(this, "Harap masukkan kode verifikasi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyEmailCode(verificationCode: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val username = intent.getStringExtra("username") ?: return@launch
                val verifyRequest = VerifyEmailRequest(username, verificationCode)

                val response = ConfigApi.apiService.verifyEmail(verifyRequest)

                if (response.isSuccessful) {
                    Toast.makeText(this@VerifyEmailActivity, "Verifikasi berhasil!", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(this@VerifyEmailActivity, "Kode verifikasi salah!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@VerifyEmailActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Menutup VerifyEmailActivity
    }
}
