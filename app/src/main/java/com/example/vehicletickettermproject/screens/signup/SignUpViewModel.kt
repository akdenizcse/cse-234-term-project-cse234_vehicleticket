package com.example.vehicletickettermproject.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.data.Reservation
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _firstname = MutableStateFlow("")
    val firstname: StateFlow<String> get() = _firstname

    private val _lastname = MutableStateFlow("")
    val lastname: StateFlow<String> get() = _lastname

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

    fun onFirstnameChange(newFirstname: String) {
        _firstname.value = newFirstname
    }

    fun onLastnameChange(newLastname: String) {
        _lastname.value = newLastname
    }

    fun signUp(navController: NavController, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                firebaseAuth.createUserWithEmailAndPassword(_email.value, _password.value).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        val userId = user?.uid

                        val userMap = hashMapOf(
                            "id" to userId,
                            "firstname" to _firstname.value,
                            "lastname" to _lastname.value,
                            "email" to _email.value,
                            "reservations" to emptyList<Reservation>() // initialize with empty list
                        )

                        userId?.let {
                            firestoreDatabase.collection("users").document(it).set(userMap).addOnCompleteListener { dbTask ->                                if(dbTask.isSuccessful){
                                    navController.navigate(VehicleTicketScreens.search.name) {
                                        popUpTo(VehicleTicketScreens.signup.name) { inclusive = true }
                                    }
                                    _firstname.value = ""
                                    _lastname.value = ""
                                    _email.value = ""
                                    _password.value = ""
                                    onSuccess()
                                }
                                else{
                                    onError("Sign up failed: ${dbTask.exception?.message}")
                                }
                                _isLoading.value = false
                            }
                        } ?: run{
                            onError("User ID is null")
                            _isLoading.value = false
                        }
                    } else {
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