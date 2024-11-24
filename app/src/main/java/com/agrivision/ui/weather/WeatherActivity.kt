package com.agrivision.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.agrivision.R
import com.agrivision.data.response.CuacaItemItem
import com.agrivision.data.response.DataItem
import com.agrivision.data.response.ResponseWeather
import com.agrivision.data.retrofit.ApiConfig
import com.agrivision.databinding.ActivityWeatherBinding
import com.agrivision.ui.adapter.WeatherAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherAdapter: WeatherAdapter

    companion object {
        private const val TAG = "WeatherActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView() // Setup RecyclerView untuk menampilkan data cuaca
        fetchWeatherData() // Ambil data cuaca dari API
    }

    private fun setupRecyclerView() {
        // Atur layout manager untuk RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.rvWeather.layoutManager = layoutManager

        // Tambahkan dekorasi pembatas antar item
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvWeather.addItemDecoration(itemDecoration)

        // Inisialisasi adapter tanpa data awal
        weatherAdapter = WeatherAdapter() // Gunakan constructor tanpa parameter
        binding.rvWeather.adapter = weatherAdapter
    }

    private fun flattenWeatherData(dataItems: List<DataItem>): List<CuacaItemItem> {
        return dataItems.flatMap { dataItem ->
            dataItem.cuaca.flatten() // Flatten array dua dimensi menjadi satu list
        }
    }

    private fun fetchWeatherData() {
        // Tampilkan loading saat data sedang diambil
        showLoading(true)

        // Ambil data cuaca dari API
        val client = ApiConfig.getApiService().getWeatherForecast("31.71.03.1001")
        client.enqueue(object : Callback<ResponseWeather> {
            override fun onResponse(call: Call<ResponseWeather>, response: Response<ResponseWeather>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Log untuk memastikan data berhasil diterima
                        Log.d(TAG, "Data received: ${responseBody.data}")

                        val allWeatherList = flattenWeatherData(responseBody.data)
                        updateWeatherList(allWeatherList)
                    } else {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "Fetch failed: ${t.message}")
            }
        })
    }

    private fun updateWeatherList(weatherList: List<CuacaItemItem>) {
        if (weatherList.isNotEmpty()) {
            // Update adapter dengan data cuaca yang diterima dari API
            weatherAdapter.submitList(weatherList)
        } else {
            Log.e(TAG, "No weather data to display")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        // Menampilkan progress bar saat data sedang dimuat
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
