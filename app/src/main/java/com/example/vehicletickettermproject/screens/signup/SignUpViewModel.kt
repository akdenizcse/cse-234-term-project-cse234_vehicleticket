package com.example.vehicletickettermproject.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

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

    fun signUp(navController: NavController, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                firebaseAuth.createUserWithEmailAndPassword(_email.value, _password.value).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home") {
                            popUpTo(VehicleTicketScreens.signup.name) { inclusive = true }
                        }
                        _email.value = ""
                        _password.value = ""
                        onSuccess()
                    } 
                    else {
                        onError("Sign up failed: ${task.exception?.message}")
                    }
                    _isLoading.value = false

                }
            } catch (e: Exception) {

                onError("An error occurred. Please try again.")
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}