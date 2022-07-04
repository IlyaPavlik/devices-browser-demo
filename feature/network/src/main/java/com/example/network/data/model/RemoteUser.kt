package com.example.network.data.model

data class RemoteUser(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: Long,
) {

    data class Address(
        val city: String,
        val postalCode: Int,
        val street: String,
        val streetCode: String,
        val country: String
    )
}
