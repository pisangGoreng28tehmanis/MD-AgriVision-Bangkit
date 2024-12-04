package com.agrivision.data.retrofit

import com.agrivision.data.FertilizerData
import com.agrivision.data.request.ChatbotRequest
import com.agrivision.data.response.*
import com.example.agrivision.api.ApiResponse
import com.example.agrivision.api.LoginRequest
import com.example.agrivision.api.LoginResponse
import com.example.agrivision.api.RegisterRequest
import com.example.agrivision.api.VerifyRequest
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

    @GET("articles/{id}")
    suspend fun getArticlesDetail(
        @Path("id") id: String
    ): Response<ArticleResponseItem>

    // Register
    @POST("users/register")
    suspend fun registerUser(
        @Body body: RegisterRequest
    ): Response<ApiResponse>

    // Verify Email
    @POST("users/verify-email")
    suspend fun verifyEmail(
        @Body body: VerifyRequest
    ): Response<ApiResponse>

    // Login
    @POST("users/login")
    suspend fun loginUser(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    // Chatbot
    @POST("users/chatbot")
    suspend fun askChatbot(
        @Body body: ChatbotRequest
    ): Response<ChatbotResponse>
}
