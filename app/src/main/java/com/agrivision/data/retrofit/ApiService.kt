package com.agrivision.data.retrofit

import com.agrivision.data.response.ResponseCCWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Mendapatkan data prakiraan cuaca berdasarkan latitude dan longitude.
     *
     * @param latitude Latitude lokasi.
     * @param longitude Longitude lokasi.
     * @return Call<ResponseWeather>
     */
    @GET("weather/forecast")
    fun getWeatherForecast(
        @Query("lat") latitude: Double,    // Parameter latitude
        @Query("lon") longitude: Double    // Parameter longitude
    ): Call<ResponseCCWeather>
}
