package com.example.ui.home.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.device.data.model.Device
import com.example.ui.home.R
import com.example.uicore.compose.DeviceBrowserTheme

private val DeviceIconSize = 200.dp
private val RollerShutterSliderSize = DpSize(120.dp, 50.dp)

@Composable
internal fun DeviceSteeringScreen(
    viewModel: DeviceSteeringViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val deviceState = viewModel.deviceState.collectAsState()

    Content(
        deviceState = deviceState.value,
        onToggleClick = viewModel::onToggleClick,
        onSliderChange = viewModel::onSliderChange,
        onBackClick = onBackClick
    )
}

@Composable
private fun Content(
    deviceState: DeviceSteeringState,
    onToggleClick: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onBackClick: () -> Unit
) {
    DeviceBrowserTheme {
        Scaffold(
            topBar = {
                TopBar(
                    deviceState = deviceState,
                    onBackClick = onBackClick
                )
            }
        ) {
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
}

@Composable
private fun TopBar(
    deviceState: DeviceSteeringState,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = when (deviceState) {
                    is DeviceSteeringState.Content -> deviceState.device.deviceName
                    is DeviceSteeringState.Error -> stringResource(R.string.details_title_error)
                    is DeviceSteeringState.Loading -> stringResource(R.string.details_title_loading)
                }
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
private fun DeviceDetails(
    device: Device,
    onToggleClick: () -> Unit,
    onSliderChange: (Float) -> Unit
) = Box(modifier = Modifier.padding(DeviceBrowserTheme.dimensions.intendMedium)) {
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
            .size(DeviceIconSize)
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
            .size(DeviceIconSize)
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
private fun Error() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Text(text = stringResource(R.string.details_error_loading))
}

@Composable
private fun Loading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
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
                rotationZ = 90f
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
                    placeable.place(0, -placeable.height)
                }
            }
            .size(RollerShutterSliderSize),
        value = value,
        valueRange = valueRange,
        onValueChange = onValueChange
    )
}
