package com.agrivision.ui.weather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agrivision.R
import com.agrivision.data.response.RamalanItem
import com.agrivision.data.response.ResponseCCWeather
import com.agrivision.data.retrofit.ApiConfig
import com.agrivision.databinding.ActivityWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        binding.rvWeather.layoutManager = LinearLayoutManager(this)
        weatherAdapter = WeatherAdapter()
        binding.rvWeather.adapter = weatherAdapter

        // Fetch weather data for specific latitude and longitude
        fetchWeatherForecast(-6.362060, 106.840490)  // Example coordinates (change as needed)
    }

    private fun fetchWeatherForecast(latitude: Double, longitude: Double) {
        val client = ApiConfig.getApiService().getWeatherForecast(latitude, longitude)
        client.enqueue(object : Callback<ResponseCCWeather> {
            override fun onResponse(
                call: Call<ResponseCCWeather>,
                response: Response<ResponseCCWeather>
            ) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    if (weatherData != null) {
                        binding.tvCityName.text = weatherData.kota // Access the city name
                        // Clear existing data
                        val ramalanList = ArrayList<RamalanItem>()

                        // Add each ramalan for each date to the list
                        weatherData.ramalan.forEach { ramalanPerTanggal ->
                            ramalanList.addAll(ramalanPerTanggal.ramalan)  // Add all forecasts for that date
                        }

                        // Set the new weather list in the adapter
                        weatherAdapter.setWeatherList(ramalanList)
                        // Assuming you want to use the description of the first item
                        //val firstDescription = ramalanList.firstOrNull()?.deskripsi ?: ""
                        //updateBackgroundBasedOnWeather(firstDescription)
                    } else {
                        Toast.makeText(this@WeatherActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@WeatherActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseCCWeather>, t: Throwable) {
                Toast.makeText(this@WeatherActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /*private fun updateBackgroundBasedOnWeather(description: String) {
        // Set gradient background based on the weather description
        Log.d("WeatherActivity", "Weather description: $description") // Debugging line
        when {
            description.contains("overcast clouds", ignoreCase = true) -> {
                binding.root.setBackgroundResource(R.drawable.gradient_sunny) // Set gradient sunny
            }
            description.contains("light rain", ignoreCase = true) -> {
                binding.root.setBackgroundResource(R.drawable.gradient_cloudy) // Set gradient cloudy
            }
            description.contains("moderate rain", ignoreCase = true) -> {
                binding.root.setBackgroundResource(R.drawable.gradient_rainy) // Set gradient rainy
            }
            else -> {
                binding.root.setBackgroundResource(R.drawable.gradient_cloudy) // Default cloudy
            }
        }
    }*/
}
