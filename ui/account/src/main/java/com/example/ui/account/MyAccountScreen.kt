package com.example.ui.account

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.user.model.User
import com.example.uicore.compose.DeviceBrowserTheme
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

private val DATE_FORMATTER = SimpleDateFormat("dd-MMMM-yyyy")

@Composable
internal fun MyAccountScreen(
    viewModel: MyAccountViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val accountState = viewModel.accountState.collectAsState()

    Content(
        accountState = accountState.value,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBirthdayChange = viewModel::onBirthdayChange,
        onCountryChange = viewModel::onCountryChange,
        onCityChange = viewModel::onCityChange,
        onStreetChange = viewModel::onStreetChange,
        onStreetCodeChange = viewModel::onStreetCodeChange,
        onPostalCodeChange = viewModel::onPostalCodeChange,
        onSaveClick = viewModel::onSaveClick,
        onBackClick = onBackClick
    )
}

@Composable
private fun Content(
    accountState: MyAccountState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBirthdayChange: (Date) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onStreetCodeChange: (String) -> Unit,
    onPostalCodeChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    DeviceBrowserTheme {
        Scaffold(
            topBar = {
                TopBar(
                    accountState = accountState,
                    onBackClick = onBackClick
                )
            }
        ) {
            when (accountState) {
                is MyAccountState.Content -> UserInfo(
                    accountState.user,
                    onFirstNameChange = onFirstNameChange,
                    onLastNameChange = onLastNameChange,
                    onBirthdayChange = onBirthdayChange,
                    onCountryChange = onCountryChange,
                    onCityChange = onCityChange,
                    onStreetChange = onStreetChange,
                    onStreetCodeChange = onStreetCodeChange,
                    onPostalCodeChange = onPostalCodeChange,
                    onSaveClick = onSaveClick
                )
                is MyAccountState.Error -> Error()
                is MyAccountState.Loading -> Loading()
            }
        }
    }
}

@Composable
private fun TopBar(
    accountState: MyAccountState,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = when (accountState) {
                    is MyAccountState.Content -> accountState.user.fullName
                    is MyAccountState.Error -> stringResource(R.string.account_title_error)
                    is MyAccountState.Loading -> stringResource(R.string.account_title_loading)
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
private fun UserInfo(
    user: User,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBirthdayChange: (Date) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onStreetCodeChange: (String) -> Unit,
    onPostalCodeChange: (String) -> Unit,
    onSaveClick: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {

    val fragmentManager = rememberFragmentManager()
    val datePicker = rememberDatePickerDialog(
        title = R.string.user_label_birthday,
        select = user.birthDate
    ) { onBirthdayChange(it) }

    Row(modifier = Modifier.fillMaxWidth()) {
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.firstName,
            label = stringResource(R.string.user_label_first_name),
            onValueChange = onFirstNameChange
        )
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.lastName,
            label = stringResource(R.string.user_label_last_name),
            onValueChange = onLastNameChange
        )
    }

    AccountTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                datePicker.show(fragmentManager, null)
            },
        enabled = false,
        value = DATE_FORMATTER.format(user.birthDate),
        label = stringResource(R.string.user_label_birthday)
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.address.country,
            label = stringResource(R.string.user_label_address_country),
            onValueChange = onCountryChange
        )
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.address.city,
            label = stringResource(R.string.user_label_address_city),
            onValueChange = onCityChange
        )
    }

    AccountTextField(
        modifier = Modifier.fillMaxWidth(),
        value = user.address.street,
        label = stringResource(R.string.user_label_address_street),
        onValueChange = onStreetChange
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.address.streetCode,
            label = stringResource(R.string.user_label_address_street_code),
            onValueChange = onStreetCodeChange
        )
        AccountTextField(
            modifier = Modifier.weight(1f),
            value = user.address.postalCode.toString(),
            label = stringResource(R.string.user_label_address_postal_code),
            keyboardType = KeyboardType.Number,
            onValueChange = onPostalCodeChange
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onClick = onSaveClick
    ) {
        Text(stringResource(R.string.user_save))
    }
}

@Composable
private fun AccountTextField(
    modifier: Modifier,
    value: String,
    label: String,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit = {}
) = TextField(
    modifier = modifier,
    value = value,
    label = { Text(label) },
    singleLine = true,
    enabled = enabled,
    colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        disabledTextColor = LocalContentColor.current
    ),
    keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = keyboardType
    ),
    onValueChange = onValueChange
)

@Composable
private fun Error() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Text(text = stringResource(R.string.user_error_loading))
}

@Composable
private fun Loading() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

@Composable
private fun rememberDatePickerDialog(
    @StringRes title: Int,
    select: Date? = null,
    bounds: CalendarConstraints? = null,
    onDateSelected: (Date) -> Unit = {},
): MaterialDatePicker<Long> {
    val datePicker = remember {
        MaterialDatePicker.Builder.datePicker()
            .setSelection(
                (select?.time ?: Date().time) + 24.hours.toLong(DurationUnit.MILLISECONDS)
            )
            .setCalendarConstraints(bounds)
            .setTitleText(title)
            .build()
    }

    DisposableEffect(datePicker) {
        val listener = MaterialPickerOnPositiveButtonClickListener<Long> {
            if (it != null) onDateSelected(Date(it))
        }
        datePicker.addOnPositiveButtonClickListener(listener)
        onDispose {
            datePicker.removeOnPositiveButtonClickListener(listener)
        }
    }

    return datePicker
}

@Composable
private fun rememberFragmentManager(): FragmentManager {
    val context = LocalContext.current
    return remember(context) {
        context.getActivity()!!.supportFragmentManager
    }
}

private fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
