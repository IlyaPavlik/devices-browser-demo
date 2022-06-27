package com.example.datastore.data

import android.content.Context
import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DemoSavedDataRepositoryTest {

    private val dataString = "{\n" +
            "  \"devices\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"deviceName\": \"Lampe - Cuisine\",\n" +
            "      \"intensity\": 50,\n" +
            "      \"mode\": \"ON\",\n" +
            "      \"productType\": \"Light\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"deviceName\": \"Volet roulant - Salon\",\n" +
            "      \"position\": 70,\n" +
            "      \"productType\": \"RollerShutter\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"user\": {\n" +
            "    \"firstName\": \"John\",\n" +
            "    \"lastName\": \"Doe\",\n" +
            "    \"address\": {\n" +
            "      \"city\": \"Issy-les-Moulineaux\",\n" +
            "      \"postalCode\": 92130,\n" +
            "      \"street\": \"rue Michelet\",\n" +
            "      \"streetCode\": \"2B\",\n" +
            "      \"country\": \"France\"\n" +
            "    },\n" +
            "    \"birthDate\": 813766371000\n" +
            "  }\n" +
            "}"

    private val mockedResources = mockk<Resources> {
        every { openRawResource(any()) } returns dataString.byteInputStream()
    }
    private val mockedContext = mockk<Context> {
        every { resources } returns mockedResources
    }
    private lateinit var demoSavedDataRepository: DemoSavedDataRepository

    @Before
    fun setUp() {
        demoSavedDataRepository = DemoSavedDataRepository(mockedContext)
    }

    @Test
    fun correctData_ReturnsDevices() = runTest {
        val devices = demoSavedDataRepository.getSavedDevices()
        assertEquals(devices.size, 2)
    }

    @Test
    fun correctData_ReturnsUser() = runTest {
        val user = demoSavedDataRepository.getSavedUser()

        assertEquals(user.firstName, "John")
        assertEquals(user.lastName, "Doe")
        assertEquals(user.birthDate, 813766371000)
    }

}
