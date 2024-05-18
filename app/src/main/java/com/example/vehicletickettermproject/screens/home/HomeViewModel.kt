package com.example.vehicletickettermproject.screens.home

import androidx.lifecycle.ViewModel
import com.example.vehicletickettermproject.data.VTUser
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow





class HomeViewModel : ViewModel(){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _user = MutableStateFlow<VTUser?>(null)
    val user: StateFlow<VTUser?> get() = _user

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestoreDatabase.collection("users").document(userId).get().addOnSuccessListener { documentSnapshot ->
                // this creates a VTUser instance using the info from database
                _user.value = documentSnapshot.toObject(VTUser::class.java)
                _isLoading.value = false
            }.addOnFailureListener {
                _isLoading.value = false
            }
        } else {
            _isLoading.value = false
        }
    }
}