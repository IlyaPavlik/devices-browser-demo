package com.example.feature.user

import com.example.datastore.data.SavedDataApi
import com.example.feature.user.mapper.toSavedUser
import com.example.feature.user.mapper.toUser
import com.example.feature.user.model.User
import com.example.network.data.RemoteDataApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val savedDataApi: SavedDataApi,
    private val remoteDataApi: RemoteDataApi
) {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.onStart { loadSavedUser() }

    suspend fun saveUser(user: User) {
        savedDataApi.saveUser(user.toSavedUser())
        _user.value = user
    }

    private suspend fun loadSavedUser() {
        (savedDataApi.getSavedUser()?.toUser() ?: remoteDataApi.getSavedUser().toUser())
            .also { user -> _user.value = user }
    }
}
