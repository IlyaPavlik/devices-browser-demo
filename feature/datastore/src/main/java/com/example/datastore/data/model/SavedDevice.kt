package com.example.datastore.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SavedDevice(
    val id: Long,
    val deviceName: String,
    val intensity: Int?,
    val mode: Mode?,
    val position: Int?,
    val temperature: Float?,
    val productType: Type,
) {

    enum class Mode {
        @JsonProperty("ON")
        On,

        @JsonProperty("OFF")
        Off
    }

    enum class Type {
        Light,
        RollerShutter,
        Heater
    }
}
