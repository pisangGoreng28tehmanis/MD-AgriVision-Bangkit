package com.agrivision

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.agrivision.data.local.DataStoreManager
import com.agrivision.databinding.ActivityMainBinding
import com.agrivision.ui.oauth.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



