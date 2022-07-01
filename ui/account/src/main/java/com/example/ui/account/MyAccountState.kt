package com.example.ui.account

import com.example.feature.user.model.User

sealed class MyAccountState {
    data class Content(val user: User) : MyAccountState()
    object Loading : MyAccountState()
    object Error : MyAccountState()
}
