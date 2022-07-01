package com.example.ui.home.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.device.data.model.Device
import com.example.ui.home.R
import com.example.uicore.compose.DeviceBrowserTheme

@Composable
internal fun DeviceSteeringScreen(
    viewModel: DeviceSteeringViewModel = viewModel()
) {
    val deviceState = viewModel.deviceState.collectAsState()

    Content(
        deviceState = deviceState.value,
        onToggleClick = viewModel::onToggleClick,
        onSliderChange = viewModel::onSliderChange
    )
}

@Composable
private fun Content(
    deviceState: DeviceSteeringState,
    onToggleClick: () -> Unit,
    onSliderChange: (Float) -> Unit
) {
    DeviceBrowserTheme {
        when (deviceState) {
            is DeviceSteeringState.Content -> DeviceDetails(
                device = deviceState.device,
                onToggleClick = onToggleClick,
                onSliderChange = onSliderChange
            )
            is DeviceSteeringState.Error -> Error()
            is DeviceSteeringState.Loading -> Loading()
        }
    }
}

@Composable
private fun DeviceDetails(
    device: Device,
    onToggleClick: () -> Unit,
    onSliderChange: (Float) -> Unit
) {
    when (device) {
        is Device.Heater -> HeaterDeviceScreen(
            heaterDevice = device,
            onToggleClick = onToggleClick,
            onTemperatureChange = onSliderChange
        )
        is Device.Light -> LightDeviceScreen(
            lightDevice = device,
            onToggleClick = onToggleClick,
            onIntensityChange = onSliderChange
        )
        is Device.RollerShutter -> RollerShutterDeviceScreen(
            rollerShutterDevice = device,
            onPositionChange = onSliderChange
        )
    }
}

@Composable
private fun LightDeviceScreen(
    lightDevice: Device.Light,
    onToggleClick: () -> Unit,
    onIntensityChange: (Float) -> Unit
) = Column(horizontalAlignment = Alignment.CenterHorizontally) {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        modifier = Modifier
            .size(200.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggleClick
            ),
        painter = painterResource(
            when (lightDevice.mode) {
                Device.Mode.On -> R.drawable.ic_bulb_on
                Device.Mode.Off -> R.drawable.ic_bulb_off
            }
        ),
        contentDescription = null
    )

    Text(text = stringResource(R.string.intensity, lightDevice.intensity))

    Slider(
        value = lightDevice.intensity.toFloat(),
        valueRange = Device.Light.INTENSITY_RANGE,
        onValueChange = { onIntensityChange(it) }
    )
}

@Composable
private fun RollerShutterDeviceScreen(
    rollerShutterDevice: Device.RollerShutter,
    onPositionChange: (Float) -> Unit
) = Row(
    modifier = Modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
        painter = painterResource(R.drawable.ic_shutter),
        contentDescription = null
    )

    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.position, rollerShutterDevice.position))

        VerticalSlider(
            value = rollerShutterDevice.position.toFloat(),
            valueRange = Device.RollerShutter.POSITION_RANGE,
            onValueChange = onPositionChange
        )
    }
}

@Composable
private fun HeaterDeviceScreen(
    heaterDevice: Device.Heater,
    onToggleClick: () -> Unit,
    onTemperatureChange: (Float) -> Unit
) = Column(horizontalAlignment = Alignment.CenterHorizontally) {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        modifier = Modifier
            .size(200.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggleClick
            ),
        painter = painterResource(
            when (heaterDevice.mode) {
                Device.Mode.On -> R.drawable.ic_heater_on
                Device.Mode.Off -> R.drawable.ic_heater_off
            }
        ),
        contentDescription = null
    )

    Text(text = stringResource(R.string.temperature, heaterDevice.temperature))

    Slider(
        value = heaterDevice.temperature,
        valueRange = Device.Heater.TEMPERATURE_RANGE,
        onValueChange = { onTemperatureChange(it) }
    )
}

@Composable
private fun Error() = Box(contentAlignment = Alignment.Center) {
    Text(text = stringResource(R.string.details_error_loading))
}

@Composable
private fun Loading() = Box(contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
}

@Composable
private fun VerticalSlider(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Slider(
        modifier = Modifier
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxHeight,
                        minHeight = constraints.minWidth,
                        maxHeight = constraints.maxHeight,
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .width(120.dp)
            .height(50.dp),
        value = value,
        valueRange = valueRange,
        onValueChange = onValueChange
    )
}
