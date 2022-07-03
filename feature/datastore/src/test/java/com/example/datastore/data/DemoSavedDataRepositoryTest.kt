package com.example.datastore.data

import com.example.datastore.data.model.DemoData
import com.example.datastore.data.model.SavedDevice
import com.example.datastore.data.model.SavedUser
import com.example.datastore.data.source.PreferencesDataSource
import com.example.datastore.data.source.RawDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DemoSavedDataRepositoryTest {

    private val demoData = DemoData(
        devices = listOf(
            SavedDevice(
                id = 0,
                deviceName = "Heater",
                intensity = null,
                mode = SavedDevice.Mode.On,
                position = null,
                temperature = 18f,
                productType = SavedDevice.Type.Heater
            )
        ),
        user = SavedUser(
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
    )
    private val rawDataSource = mockk<RawDataSource> {
        every { getDemoData() } returns demoData
    }
    private val preferencesDataSource = mockk<PreferencesDataSource> {
        every { getUser() } returns demoData.user
        every { saveUser(any()) } returns Unit
    }
    private lateinit var demoSavedDataRepository: DemoSavedDataRepository

    @Before
    fun setUp() {
        demoSavedDataRepository = DemoSavedDataRepository(
            rawDataSource,
            preferencesDataSource
        )
    }

    @Test
    fun correctData_ReturnsDevices() = runTest {
        val devices = demoSavedDataRepository.getSavedDevices()
        assertEquals(devices.size, 1)
    }

    @Test
    fun correctData_ReturnsUser() = runTest {
        val user = demoSavedDataRepository.getSavedUser()

        assertEquals(user.firstName, demoData.user.firstName)
        assertEquals(user.lastName, demoData.user.lastName)
        assertEquals(user.birthDate, demoData.user.birthDate)
    }

    @Test
    fun correctData_SaveUser() = runTest {
        demoSavedDataRepository.saveUser(demoData.user)

        verify { preferencesDataSource.saveUser(demoData.user) }
    }
}
