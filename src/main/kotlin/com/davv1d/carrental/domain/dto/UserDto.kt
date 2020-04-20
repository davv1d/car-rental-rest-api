package com.davv1d.carrental.domain.dto

class UserDto(
        val id: Int = 0,
        val username: String = "",
        val email: String = "",
        val role: String = "",
        val active: Boolean = true)