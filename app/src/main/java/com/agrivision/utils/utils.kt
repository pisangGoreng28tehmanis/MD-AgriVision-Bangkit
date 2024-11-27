package com.agrivision.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.agrivision.data.response.RamalanItem
import kotlinx.coroutines.tasks.await
import com.agrivision.data.retrofit.ApiConfig
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun fetchCurrentLocation(context: Context): Location? {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val locationTask = fusedLocationClient.lastLocation
        return try {
            locationTask.result
        } catch (e: Exception) {
            null
        }
    } else {
        throw SecurityException("Izin lokasi belum diberikan.")
    }
}


suspend fun fetchWeatherForecast(
    latitude: Double,
    longitude: Double
): Pair<String, List<RamalanItem>> = withContext(Dispatchers.IO) {
    try {
        val response = ApiConfig.getApiService().getWeatherForecast(latitude, longitude)

        if (response.isSuccessful) {
            val weatherData = response.body()
            val cityName = weatherData?.kota ?: "No Info"
            val ramalanList = weatherData?.ramalan?.flatMap { it.ramalan } ?: emptyList()
            return@withContext cityName to ramalanList
        } else {
            throw Exception("Gagal memuat data: ${response.message()}")
        }
    } catch (e: Exception) {
        throw Exception("Terjadi kesalahan: ${e.message}")
    }
}

//suspend fun fetchLatestWeatherForecast(
//    latitude: Double,
//    longitude: Double
//): Triple<String, String, String> = withContext(Dispatchers.IO) {
//    val response = ApiConfig.getApiService().getWeatherForecast(latitude, longitude)
//    val cityName = response.kota ?: "Tidak diketahui"
//    val currentWeather = response.ramalan.firstOrNull()?.ramalan?.firstOrNull()
//
//    val temperature = currentWeather?.suhu ?: "?"
//    val description = currentWeather?.deskripsi ?: "Deskripsi tidak tersedia"
//
//    Triple(cityName, "$temperature°C", description)
//}
