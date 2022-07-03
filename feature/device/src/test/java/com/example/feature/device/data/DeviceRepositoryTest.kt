package com.example.feature.device.data

import app.cash.turbine.test
import com.example.datastore.data.SavedDataApi
import com.example.datastore.data.model.SavedDevice
import com.example.feature.device.data.model.Device
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DeviceRepositoryTest {

    private val testDevice = SavedDevice(
        id = 0,
        deviceName = "Light",
        intensity = 50,
        mode = SavedDevice.Mode.On,
        position = null,
        temperature = null,
        productType = SavedDevice.Type.Light
    )
    private val savedDataApi = mockk<SavedDataApi> {
        coEvery { getSavedDevices() } returns listOf(
            testDevice.copy(id = 1),
            testDevice.copy(id = 2)
        )
    }

    private lateinit var deviceRepository: DeviceRepository

    @Before
    fun setUp() {
        deviceRepository = DeviceRepository(savedDataApi)
    }

    @Test
    fun correctData_returnTwoDevice() = runTest {
        val device = deviceRepository.devices.first()
        val expected = Device.Light(
            id = testDevice.id,
            deviceName = testDevice.deviceName,
            intensity = testDevice.intensity!!,
            mode = Device.Mode.On
        )
        assertEquals(listOf(expected.copy(id = 1), expected.copy(id = 2)), device)
    }

    @Test
    fun correctData_deleteDevice() = runTest {
        deviceRepository.devices.test {
            deviceRepository.deleteDevice(1)
            skipItems(1)
            assertEquals(1, awaitItem().size)
        }
    }
}
