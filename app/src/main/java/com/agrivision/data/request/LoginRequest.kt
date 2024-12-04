package com.example.agrivision.api

data class LoginRequest(
    val emailOrUsername: String,
    val password: String
)
