package com.xzakota.model

import com.xzakota.model.Authentication.AuthType

data class Authentication @JvmOverloads constructor(
    val username : String?,
    val password : String?,
    val type : AuthType = AuthType.APPLICATION
) {
    enum class AuthType {
        APPLICATION
    }
}
