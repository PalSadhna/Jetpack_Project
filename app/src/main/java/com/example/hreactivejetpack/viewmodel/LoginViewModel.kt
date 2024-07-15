package com.example.hreactivejetpack.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hreactivejetpack.model.AllEmployeeResponse
import com.example.hreactivejetpack.model.LoginResponse
import com.example.hreactivejetpack.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repository = LoginRepository()
    private val _loginCards = MutableLiveData<AllEmployeeResponse>()
    val loginCards: LiveData<AllEmployeeResponse> = _loginCards
  /*  fun fetchLoginCards(token: String, orgId: String) {
        viewModelScope.launch {
            try {
                val cards = repository.getLoginData(token,orgId)
                _loginCards.value = cards
            } catch (e: Exception) {
                // Handle error
            }
        }
    }*/
}