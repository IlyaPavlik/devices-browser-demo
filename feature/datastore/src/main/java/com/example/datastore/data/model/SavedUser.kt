package com.example.datastore.data.model

data class SavedUser(
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
