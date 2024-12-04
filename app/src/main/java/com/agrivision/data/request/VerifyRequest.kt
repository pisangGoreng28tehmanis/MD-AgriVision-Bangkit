package com.example.agrivision.api

data class VerifyRequest(
    val username: String,
    val verificationCode: String
)
