package com.example.vehicletickettermproject.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.vehicletickettermproject.data.BusJourney
import com.example.vehicletickettermproject.data.VTUser
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow





class HomeViewModel : ViewModel(){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _user = MutableStateFlow<VTUser?>(null)
    val user: StateFlow<VTUser?> get() = _user

    private val _busJourneys = MutableStateFlow<List<BusJourney>>(emptyList())
    val busJourneys: StateFlow<List<BusJourney>> get() = _busJourneys

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchUserData()
        fetchBusJourneyData()
    }

    private fun fetchUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get().addOnSuccessListener { documentSnapshot ->
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

    private fun fetchBusJourneyData() {
        firestore.collection("busJourney").get().addOnSuccessListener { result ->
            val journeys = result.documents.mapNotNull { document ->
                try {
                    document.toObject(BusJourney::class.java)?.copy(id = document.id)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error parsing bus journey document", e)
                    null
                }
            }
            _busJourneys.value = journeys
            Log.d("HomeViewModel", "Fetched bus journeys: $journeys")
        }.addOnFailureListener { exception ->
            Log.e("HomeViewModel", "Error fetching bus journeys", exception)
        }
    }
}