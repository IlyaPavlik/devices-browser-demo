package com.example.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.user.UserRepository
import com.example.feature.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _localUser = MutableStateFlow<User?>(null)
    val accountState by lazy {
        _localUser
            .onStart {
                _localUser.value = userRepository.user.filterNotNull().first()
            }
            .map { user ->
                user?.let { MyAccountState.Content(it) } ?: MyAccountState.Loading
            }
            .catch { exception ->
                Timber.e(exception, "Error while loading account")
                emit(MyAccountState.Error)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, MyAccountState.Loading)
    }

    private val _navigateBack = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateBack = _navigateBack.asSharedFlow()

    fun onFirstNameChange(value: String) {
        _localUser.applyUserChanges { copy(firstName = value) }
    }

    fun onLastNameChange(value: String) {
        _localUser.applyUserChanges { copy(lastName = value) }
    }

    fun onBirthdayChange(value: Date) {
        _localUser.applyUserChanges { copy(birthDate = value) }
    }

    fun onCountryChange(value: String) {
        _localUser.applyUserChanges { copy(address = address.copy(country = value)) }
    }

    fun onCityChange(value: String) {
        _localUser.applyUserChanges { copy(address = address.copy(city = value)) }
    }

    fun onStreetChange(value: String) {
        _localUser.applyUserChanges { copy(address = address.copy(street = value)) }
    }

    fun onStreetCodeChange(value: String) {
        _localUser.applyUserChanges { copy(address = address.copy(streetCode = value)) }
    }

    fun onPostalCodeChange(value: String) {
        val postCode = value.filter { it.isDigit() }.toInt()
        _localUser.applyUserChanges { copy(address = address.copy(postalCode = postCode)) }
    }

    fun onSaveClick() = viewModelScope.launch {
        _localUser.value?.let {
            userRepository.saveUser(it)
            _navigateBack.tryEmit(Unit)
        }
    }

    private fun MutableStateFlow<User?>.applyUserChanges(new: User.() -> User) {
        _localUser.value?.let { user ->
            value = user.new()
        }
    }
}
