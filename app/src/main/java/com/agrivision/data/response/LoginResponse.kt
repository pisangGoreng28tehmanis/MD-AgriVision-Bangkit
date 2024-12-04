package com.example.agrivision.api

data class LoginResponse(
    val token: String,
    val message: String,
    val status: Int
)
