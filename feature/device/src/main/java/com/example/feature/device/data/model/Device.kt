package com.example.feature.device.data.model

sealed class Device(open val id: Long, open val deviceName: String) {

    data class Light(
        override val id: Long,
        override val deviceName: String,
        val intensity: Int,
        val mode: Mode
    ) : Device(id, deviceName) {

        companion object {
            val INTENSITY_RANGE = 1f..100f
        }
    }

    data class RollerShutter(
        override val id: Long,
        override val deviceName: String,
        val position: Int
    ) : Device(id, deviceName) {

        companion object {
            val POSITION_RANGE = 1f..100f
        }
    }

    data class Heater(
        override val id: Long,
        override val deviceName: String,
        val temperature: Float,
        val mode: Mode
    ) : Device(id, deviceName) {

        companion object {
            val TEMPERATURE_RANGE = 8f..28f
        }
    }

    enum class Mode {
        On, Off
    }
}
