package com.agrivision

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.agrivision.databinding.ActivityMainBinding
import com.agrivision.ui.oauth.LoginActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah pengguna sudah login
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            // Jika belum login, arahkan ke login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Agar pengguna tidak bisa kembali ke MainActivity
        } else {
            enableEdgeToEdge() // Enable edge-to-edge setelah login

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)

            // Menghubungkan BottomNavigationView dengan NavController
            binding.navView.setOnApplyWindowInsetsListener(null)
            binding.navView.setPadding(0, 0, 0, 0)
            navView.setupWithNavController(navController)
        }
    }
}
