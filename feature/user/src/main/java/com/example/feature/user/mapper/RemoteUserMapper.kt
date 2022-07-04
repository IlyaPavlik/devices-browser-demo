package com.example.feature.user.mapper

import com.example.feature.user.model.User
import com.example.network.data.model.RemoteUser
import java.util.*

internal fun RemoteUser.toUser() = User(
    firstName = firstName,
    lastName = lastName,
    address = User.Address(
        city = address.city,
        postalCode = address.postalCode,
        street = address.street,
        streetCode = address.streetCode,
        country = address.country
    ),
    birthDate = Date(birthDate)
)
