package com.example.vehicletickettermproject.screens.signin

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SignInViewModel() : ViewModel() {

    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }


    fun signIn(navController:NavController,onError : (String) -> Unit){
        navController.navigate(VehicleTicketScreens.home.name){
            // this deletes the stack up to signin
            popUpTo(VehicleTicketScreens.signin.name)
        }
    }


}