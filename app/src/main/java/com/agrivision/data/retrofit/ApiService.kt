package com.agrivision.data.retrofit

import com.agrivision.data.response.ResponseWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * Mendapatkan data prakiraan cuaca seluruh kelurahan/desa di Indonesia.
     *
     * @param adm4 Kode wilayah administrasi tingkat IV dari Kementerian Dalam Negeri.
     * @return Call<ResponseWeather>
     */

    @GET("publik/prakiraan-cuaca")
    fun getWeatherForecast(
        @Query("adm4") adminCode: String // Parameter kode wilayah
    ): Call<ResponseWeather>
}
