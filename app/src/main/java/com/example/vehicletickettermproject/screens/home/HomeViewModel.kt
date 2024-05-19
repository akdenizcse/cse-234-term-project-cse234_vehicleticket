package com.example.vehicletickettermproject.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.vehicletickettermproject.data.BusJourney
import com.example.vehicletickettermproject.data.VTUser
import com.google.firebase.Timestamp
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

    private val _isUserDataLoading = MutableStateFlow(true)
    val isUserDataLoading: StateFlow<Boolean> get() = _isUserDataLoading

    init {
        fetchUserData()
        fetchBusJourneyData()
    }

    private fun fetchUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get().addOnSuccessListener { documentSnapshot ->

                // this creates a VTUser instance using the info from database
                if (documentSnapshot.exists()) {
                    _user.value = documentSnapshot.toObject(VTUser::class.java)
                }
                _isUserDataLoading.value = false
            }.addOnFailureListener {
                _isUserDataLoading.value = false
            }
        } else {
            _isUserDataLoading.value = false
        }
    }

    private fun fetchBusJourneyData() {
        firestore.collection("busJourney").get().addOnSuccessListener { result ->
            val journeys = result.documents.mapNotNull { document ->
                try {
                    val data = document.data ?: return@mapNotNull null
                    val availableSeats = (data["availableSeats"] as? Map<String, String?>)?.mapValues {
                        // userid is empty string when seat isnt sold, so convert it to null
                        if (it.value.isNullOrEmpty()) null else it.value
                    } ?: emptyMap()

                    BusJourney(
                        id = document.id,
                        fromPlace = data["fromPlace"] as? String ?: "",
                        toPlace = data["toPlace"] as? String ?: "",
                        beginDateTime = data["beginDateTime"] as? Timestamp,
                        duration = data["duration"] as? String ?: "",
                        price = (data["price"] as? Number)?.toInt() ?: 0,
                        totalSeats = (data["totalSeats"] as? Number)?.toInt() ?: 0,
                        availableSeats = availableSeats
                    )
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error: couldnt parse bus journey document", e)
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