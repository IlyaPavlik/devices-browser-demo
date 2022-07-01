package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.device.data.model.Device
import com.example.uicore.compose.DeviceBrowserTheme

@Composable
internal fun HomePageScreen(
    viewModel: HomePageViewModel = viewModel(),
    onDeviceClick: (Device) -> Unit
) {
    val state = viewModel.homePageState.collectAsState()

    Content(
        state = state.value,
        onFilterSelect = viewModel::onFilterSelected,
        onDeviceClick = onDeviceClick,
        onDeleteClick = viewModel::onDeleteClick
    )
}

@Composable
private fun Content(
    state: HomePageState,
    onFilterSelect: (FilterDeviceType) -> Unit,
    onDeviceClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit
) {
    DeviceBrowserTheme {
        when (state) {
            is HomePageState.Content ->
                DeviceList(
                    filter = state.filter,
                    devices = state.devices,
                    onFilterSelect = onFilterSelect,
                    onItemClick = onDeviceClick,
                    onDeleteClick = onDeleteClick
                )
            is HomePageState.Error -> Error()
            is HomePageState.Loading -> Loading()
        }
    }
}

@Composable
private fun DeviceList(
    filter: FilterDeviceType,
    devices: List<Device>,
    onFilterSelect: (FilterDeviceType) -> Unit,
    onItemClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            DeviceFilterDropdown(
                filter = filter,
                onFilterSelect = onFilterSelect
            )
        }
        items(items = devices) { device ->
            DeviceItem(device, onItemClick, onDeleteClick)
        }
    }
}

@Composable
private fun DeviceFilterDropdown(
    filter: FilterDeviceType,
    onFilterSelect: (FilterDeviceType) -> Unit
) = Box {
    var expanded by remember { mutableStateOf(false) }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(16.dp),
        text = stringResource(R.string.filter_patter, stringResource(filter.resId))
    )

    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        FilterDeviceType.values().forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onFilterSelect(it)
                    expanded = false
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(it.resId)
                )
            }
        }
    }
}

@Composable
private fun DeviceItem(
    device: Device,
    onItemClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(device) }
        .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = device.deviceName,
                fontSize = 16.sp
            )
            StatusIndicator(device)
        }

        Text(
            text = device.getDetails(),
            fontSize = 14.sp
        )
    }

    Icon(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onDeleteClick(device) },
        imageVector = Icons.Default.Delete,
        contentDescription = stringResource(R.string.delete)
    )
}

@Composable
private fun StatusIndicator(device: Device) {
    val modeStatus = when (device) {
        is Device.Heater -> device.mode
        is Device.Light -> device.mode
        else -> null
    }
    if (modeStatus != null) {
        val color = when (modeStatus) {
            Device.Mode.On -> Color.Green
            Device.Mode.Off -> Color.Red
        }
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Composable
private fun Error() = Box(contentAlignment = Alignment.Center) {
    Text(text = stringResource(R.string.home_error_loading))
}

@Composable
private fun Loading() = Box(contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
}

@Preview(showBackground = true)
@Composable
private fun HomePageScreenPreview() {
    Content(
        state = HomePageState.Content(
            filter = FilterDeviceType.All,
            devices = listOf(
                Device.Light(0, "Device 1", 0, Device.Mode.On),
                Device.Heater(0, "Device 1", 0f, Device.Mode.Off),
                Device.RollerShutter(0, "Device 1", 0),
            )
        ),
        onFilterSelect = {},
        onDeviceClick = {},
        onDeleteClick = {}
    )
}

@Composable
private fun Device.getDetails(): String = when (this) {
    is Device.Heater -> stringResource(
        R.string.temperature,
        temperature
    )
    is Device.Light -> stringResource(
        R.string.intensity,
        intensity
    )
    is Device.RollerShutter -> stringResource(
        R.string.position,
        position
    )
}
