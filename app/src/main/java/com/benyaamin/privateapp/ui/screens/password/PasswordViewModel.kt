package com.benyaamin.privateapp.ui.screens.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyaamin.privateapp.repositories.PasswordRepository
import com.benyaamin.privateapp.models.Password
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : ViewModel() {
    private val _passwordsFlow = MutableStateFlow<List<Password>>(emptyList())
    val passwordFlow: StateFlow<List<Password>> = _passwordsFlow

    init {
        reloadList()
    }

    fun reloadList() = viewModelScope.launch {
        val result = passwordRepository.getPasswords()
        _passwordsFlow.emit(result)
    }

    fun addNewPassword(password: Password) = viewModelScope.launch {
        passwordRepository.insertPassword(password)
        reloadList()
    }

    fun updatePassword(password: Password) = viewModelScope.launch {
        passwordRepository.updatePassword(password)
        reloadList()
    }

    fun deletePassword(password: Password) = viewModelScope.launch {
        passwordRepository.deletePassword(password)
        reloadList()
    }

    fun searchWith(query: String) = viewModelScope.launch {
        val result = passwordRepository.search(query)
        _passwordsFlow.emit(result)
    }

}