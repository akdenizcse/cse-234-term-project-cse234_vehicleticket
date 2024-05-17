package com.example.vehicletickettermproject.screens.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class User(val firstname: String = "", val lastname: String = "", val email: String = "")


class HomeViewModel : ViewModel(){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firebaseDatabase.getReference("users").child(userId).get().addOnSuccessListener { dataSnapshot ->
                _user.value = dataSnapshot.getValue(User::class.java)
                _isLoading.value = false
            }.addOnFailureListener {
                _isLoading.value = false
            }
        } else {
            _isLoading.value = false
        }
    }
}