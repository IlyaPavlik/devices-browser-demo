package com.example.feature.user

import com.example.datastore.data.SavedDataApi
import com.example.datastore.data.model.SavedUser
import com.example.feature.user.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

internal class UserRepositoryTest {

    private val testUser = SavedUser(
        firstName = "First name",
        lastName = "Last name",
        address = SavedUser.Address(
            city = "City",
            postalCode = 0,
            street = "Street",
            streetCode = "code",
            country = "Country"
        ),
        birthDate = 0
    )
    private val savedDataApi = mockk<SavedDataApi> {
        coEvery { getSavedUser() } returns testUser
    }
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(savedDataApi)
    }

    @Test
    fun correctData_ReturnUser() = runTest {
        val user = userRepository.user.first()
        val expect = User(
            firstName = testUser.firstName,
            lastName = testUser.lastName,
            address = User.Address(
                city = testUser.address.city,
                postalCode = testUser.address.postalCode,
                street = testUser.address.street,
                streetCode = testUser.address.streetCode,
                country = testUser.address.country
            ),
            birthDate = Date(testUser.birthDate)
        )
        assertEquals(expect, user)
    }
}
