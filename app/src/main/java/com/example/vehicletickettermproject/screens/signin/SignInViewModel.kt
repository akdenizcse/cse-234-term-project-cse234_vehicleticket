package com.example.vehicletickettermproject.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SignInViewModel() : ViewModel() {

    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }


    fun signIn(navController:NavController,onError : (String) -> Unit){
        viewModelScope.launch {
            _isLoading.value = true

            try {
                firebaseAuth.signInWithEmailAndPassword(_email.value, _password.value).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _isAuthenticated.value = true
                        navController.navigate(VehicleTicketScreens.search.name) {
                            popUpTo(VehicleTicketScreens.signin.name) { inclusive = true }
                        }
                        // when successful sign in occurs, delete the stored email and pw
                        _email.value = ""
                        _password.value = ""
                    }
                    else{
                        onError("Sign in failed: ${task.exception?.message}")
                    }
                }
            } catch (e: Exception) {
                onError("An error occurred. ${e.message}.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut(navController:NavController){
        firebaseAuth.signOut()
        _isAuthenticated.value = false

        navController.navigate(VehicleTicketScreens.signin.name) {

            popUpTo(VehicleTicketScreens.signin.name) { inclusive = true }
            launchSingleTop = true
            restoreState = false
        }

    }

}