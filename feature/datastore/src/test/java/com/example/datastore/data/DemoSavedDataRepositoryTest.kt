package com.example.datastore.data

import com.example.datastore.data.model.SavedUser
import com.example.datastore.data.source.PreferencesDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DemoSavedDataRepositoryTest {

    private val savedUser = SavedUser(
        firstName = "First name",
        lastName = "Second name",
        address = SavedUser.Address(
            city = "City",
            postalCode = 11110,
            street = "Street",
            streetCode = "Street code",
            country = "Country"
        ),
        birthDate = 0
    )
    private val preferencesDataSource = mockk<PreferencesDataSource> {
        every { getUser() } returns savedUser
        every { saveUser(any()) } returns Unit
    }
    private lateinit var demoSavedDataRepository: SavedDataRepository

    @Before
    fun setUp() {
        demoSavedDataRepository = SavedDataRepository(
            preferencesDataSource
        )
    }

    @Test
    fun correctData_ReturnsUser() = runTest {
        val user = demoSavedDataRepository.getSavedUser()

        assertEquals(user!!.firstName, savedUser.firstName)
        assertEquals(user.lastName, savedUser.lastName)
        assertEquals(user.birthDate, savedUser.birthDate)
    }

    @Test
    fun correctData_SaveUser() = runTest {
        demoSavedDataRepository.saveUser(savedUser)

        verify { preferencesDataSource.saveUser(savedUser) }
    }
}
