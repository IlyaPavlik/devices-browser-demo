package com.example.feature.device.data.model

sealed class Device(open val id: Long, open val deviceName: String) {

    data class Light(
        override val id: Long,
        override val deviceName: String,
        val intensity: Int,
        val mode: Mode
    ) : Device(id, deviceName)

    data class RollerShutter(
        override val id: Long,
        override val deviceName: String,
        val position: Int
    ) : Device(id, deviceName)

    data class Heater(
        override val id: Long,
        override val deviceName: String,
        val temperature: Int,
        val mode: Mode
    ) : Device(id, deviceName)

    enum class Mode {
        On, Off
    }
}
