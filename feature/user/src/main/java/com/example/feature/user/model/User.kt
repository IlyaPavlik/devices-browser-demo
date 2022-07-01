package com.example.feature.user.model

import java.util.*

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: Date,
) {

    val fullName: String
        get() = "$firstName $lastName"

    data class Address(
        val city: String,
        val postalCode: Int,
        val street: String,
        val streetCode: String,
        val country: String
    )
}
