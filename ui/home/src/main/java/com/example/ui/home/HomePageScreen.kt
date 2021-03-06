package com.example.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.device.data.model.Device
import com.example.feature.user.model.User
import com.example.uicore.compose.DeviceBrowserTheme

private val DeviceIndicatorSize = 8.dp

@Composable
internal fun HomePageScreen(
    viewModel: HomePageViewModel = viewModel(),
    onDeviceClick: (Device) -> Unit,
    onUserClick: () -> Unit
) {
    val state = viewModel.homePageState.collectAsState()

    Content(
        state = state.value,
        onFilterSelect = viewModel::onFilterSelected,
        onDeviceClick = onDeviceClick,
        onDeleteClick = viewModel::onDeleteClick,
        onUserClick = onUserClick,
    )
}

@Composable
private fun Content(
    state: HomePageState,
    onFilterSelect: (FilterDeviceType) -> Unit,
    onDeviceClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit,
    onUserClick: () -> Unit,
) {
    DeviceBrowserTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.home_title))
                    }
                )
            }
        ) {
            when (state) {
                is HomePageState.Content ->
                    ContentDetails(
                        details = state,
                        onFilterSelect = onFilterSelect,
                        onItemClick = onDeviceClick,
                        onDeleteClick = onDeleteClick,
                        onUserClick = onUserClick
                    )
                is HomePageState.Error -> Error()
                is HomePageState.Loading -> Loading()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentDetails(
    details: HomePageState.Content,
    onFilterSelect: (FilterDeviceType) -> Unit,
    onItemClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit,
    onUserClick: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (details.user != null) {
            item {
                UserDetails(
                    user = details.user,
                    onItemClick = onUserClick
                )
            }
        }
        item {
            DeviceFilterDropdown(
                filter = details.filter,
                onFilterSelect = onFilterSelect
            )
        }
        items(items = details.devices, key = { it.id }) { device ->
            DeviceItem(
                modifier = Modifier.animateItemPlacement(),
                device = device,
                onItemClick = onItemClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun UserDetails(
    user: User,
    onItemClick: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(DeviceBrowserTheme.dimensions.intendMedium),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(
        modifier = Modifier.padding(end = DeviceBrowserTheme.dimensions.intendSmall),
        imageVector = Icons.Default.AccountBox,
        contentDescription = null
    )
    Text(
        text = user.fullName
    )
}

@Composable
private fun DeviceFilterDropdown(
    filter: FilterDeviceType,
    onFilterSelect: (FilterDeviceType) -> Unit
) = Box {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { expanded = true }
        .padding(DeviceBrowserTheme.dimensions.intendMedium)) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.filter_patter, stringResource(filter.resId))
        )
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
    }

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
    modifier: Modifier,
    device: Device,
    onItemClick: (Device) -> Unit,
    onDeleteClick: (Device) -> Unit
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick(device) }
        .padding(DeviceBrowserTheme.dimensions.intendMedium),
    verticalAlignment = Alignment.CenterVertically
) {
    Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = device.deviceName,
                style = DeviceBrowserTheme.typography.body1
            )
            StatusIndicator(device)
        }

        Text(
            text = device.getDetails(),
            style = DeviceBrowserTheme.typography.body2
        )
    }

    Icon(
        modifier = Modifier
            .padding(DeviceBrowserTheme.dimensions.intendSmall)
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
                .padding(start = DeviceBrowserTheme.dimensions.intendSmall)
                .size(DeviceIndicatorSize)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Composable
private fun Error() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Text(text = stringResource(R.string.home_error_loading))
}

@Composable
private fun Loading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

@Preview(showBackground = true)
@Composable
private fun HomePageScreenPreview() {
    Content(
        state = HomePageState.Content(
            user = null,
            filter = FilterDeviceType.All,
            devices = listOf(
                Device.Light(0, "Device 1", 0, Device.Mode.On),
                Device.Heater(0, "Device 1", 0f, Device.Mode.Off),
                Device.RollerShutter(0, "Device 1", 0),
            )
        ),
        onFilterSelect = {},
        onDeviceClick = {},
        onDeleteClick = {},
        onUserClick = {}
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
