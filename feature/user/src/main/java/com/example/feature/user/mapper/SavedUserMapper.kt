package com.example.feature.user.mapper

import com.example.datastore.data.model.SavedUser
import com.example.feature.user.model.User
import java.util.*

internal fun SavedUser.toUser() = User(
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

internal fun User.toSavedUser() = SavedUser(
    firstName = firstName,
    lastName = lastName,
    address = SavedUser.Address(
        city = address.city,
        postalCode = address.postalCode,
        street = address.street,
        streetCode = address.streetCode,
        country = address.country
    ),
    birthDate = birthDate.time
)
