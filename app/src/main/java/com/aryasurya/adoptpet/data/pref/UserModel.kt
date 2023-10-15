package com.aryasurya.adoptpet.data.pref

data class UserModel(
    val username: String,
    val email: String,
    val password: String,
    val isLogin: Boolean = false
)