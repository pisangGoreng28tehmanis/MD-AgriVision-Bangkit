package com.agrivision.data.remote.retrofit

import com.agrivision.data.companion.FertilizerData
import com.agrivision.data.companion.LoginRequest
import com.agrivision.data.companion.RegisterRequest
import com.agrivision.data.companion.VerifyRequest
import com.agrivision.data.companion.message
import com.agrivision.data.remote.response.ArticleResponseItem
import com.agrivision.data.remote.response.ChatResponse
import com.agrivision.data.remote.response.FertilizerPrediction
import com.agrivision.data.remote.response.LoginResponse
import com.agrivision.data.remote.response.RegisterResponse
import com.agrivision.data.remote.response.ResponseCCWeather
import com.agrivision.data.remote.response.Token
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Weather Forecast
    @GET("weather/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<ResponseCCWeather>

    // Token
    @POST("/token")
    suspend fun getToken(): Response<Token>

    // Fertilizer Prediction
    @POST("/kalkulator-pupuk")
    suspend fun getFertilizerPred(
        @Header("Authorization") accessToken: String,
        @Body fertilizerBody: FertilizerData
    ): Response<FertilizerPrediction>

    // Articles
    @GET("articles/all")
    suspend fun getArticles(): Response<List<ArticleResponseItem>>

    @POST("chatbot")
    suspend fun getChatResponse(
        @Body message: message
    ): Response<ChatResponse>

    @GET("articles/{id}")
    suspend fun getArticlesDetail(
        @Path("id") id: String
    ): Response<ArticleResponseItem>

    // Register
    @POST("users/register")
    suspend fun registerUser(
        @Body body: RegisterRequest
    ): Response<RegisterResponse>

    // Verify Email
    @POST("users/verify-email")
    suspend fun verifyEmail(
        @Body body: VerifyRequest
    ): Response<RegisterResponse>

    // Login
    @POST("users/login")
    suspend fun loginUser(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    // Chatbot
}
