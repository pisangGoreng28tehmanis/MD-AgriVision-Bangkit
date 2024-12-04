package com.example.agrivision.api

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
    val nickname: String
)
