package com.example.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.user.UserRepository
import com.example.feature.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _accountState = MutableStateFlow<MyAccountState>(MyAccountState.Loading)
    val accountState = _accountState.asStateFlow()

    private val _navigateBack = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateBack = _navigateBack.asSharedFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Error while loading account")
        _accountState.value = MyAccountState.Error
    }

    init {
        viewModelScope.launch(errorHandler) {
            userRepository.user
                .onEach { user ->
                    user?.let { _accountState.value = MyAccountState.Content(it) }
                }
                .launchIn(this)
        }
    }

    fun onFirstNameChange(value: String) {
        _accountState.applyUserChanges { it.copy(firstName = value) }
    }

    fun onLastNameChange(value: String) {
        _accountState.applyUserChanges { it.copy(lastName = value) }
    }

    fun onBirthdayChange(value: Date) {
        _accountState.applyUserChanges { it.copy(birthDate = value) }
    }

    fun onCountryChange(value: String) {
        _accountState.applyUserChanges { it.copy(address = it.address.copy(country = value)) }
    }

    fun onCityChange(value: String) {
        _accountState.applyUserChanges { it.copy(address = it.address.copy(city = value)) }
    }

    fun onStreetChange(value: String) {
        _accountState.applyUserChanges { it.copy(address = it.address.copy(street = value)) }
    }

    fun onStreetCodeChange(value: String) {
        _accountState.applyUserChanges { it.copy(address = it.address.copy(streetCode = value)) }
    }

    fun onPostalCodeChange(value: String) {
        val postCode = value.filter { it.isDigit() }.toInt()
        _accountState.applyUserChanges { it.copy(address = it.address.copy(postalCode = postCode)) }
    }

    fun onSaveClick() = viewModelScope.launch {
        _accountState.getUser()?.let {
            userRepository.saveUser(it)
            _navigateBack.tryEmit(Unit)
        }
    }

    private fun MutableStateFlow<MyAccountState>.applyUserChanges(block: (User) -> User) {
        getUser()?.let { user ->
            value = MyAccountState.Content(block(user))
        }
    }

    private fun StateFlow<MyAccountState>.getUser(): User? =
        (value as? MyAccountState.Content)?.user
}
