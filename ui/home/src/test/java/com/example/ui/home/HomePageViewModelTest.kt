package com.example.ui.home

import app.cash.turbine.test
import com.example.feature.device.data.DeviceRepository
import com.example.feature.device.data.model.Device
import com.example.feature.user.UserRepository
import com.example.feature.user.model.User
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

internal class HomePageViewModelTest {

    private val testDevice = Device.Light(
        id = 0,
        deviceName = "Test device",
        intensity = 50,
        mode = Device.Mode.On
    )
    private val testUser = User(
        firstName = "First name",
        lastName = "Last name",
        address = User.Address(
            city = "City",
            postalCode = 0,
            street = "Street",
            streetCode = "code",
            country = "Country"
        ),
        birthDate = Date()
    )
    private val deviceRepository = mockk<DeviceRepository> {
        every { devices } returns flowOf(listOf(testDevice))
    }
    private val userRepository = mockk<UserRepository> {
        every { user } returns flowOf(testUser)
    }
    private lateinit var homePageViewModel: HomePageViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        homePageViewModel = HomePageViewModel(
            userRepository,
            deviceRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun homePage_ReturnLoadingState() = runTest {
        every { deviceRepository.devices } returns flowOf()
        homePageViewModel.homePageState.test {
            assertEquals(HomePageState.Loading, awaitItem())
        }
    }

    @Test
    fun homePage_ReturnErrorState() = runTest {
        every { deviceRepository.devices } returns flow { throw IllegalStateException("Test exception") }
        homePageViewModel.homePageState.test {
            assertEquals(HomePageState.Error, awaitItem())
        }
    }

    @Test
    fun homPage_ReturnData() = runTest {
        val expect = HomePageState.Content(
            user = testUser,
            filter = FilterDeviceType.All,
            devices = listOf(testDevice)
        )
        homePageViewModel.homePageState.test {
            assertEquals(expect, awaitItem())
        }
    }
}
