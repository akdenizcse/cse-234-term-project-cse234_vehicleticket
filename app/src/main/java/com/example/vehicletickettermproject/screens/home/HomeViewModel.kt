package com.example.vehicletickettermproject.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicletickettermproject.data.BusJourney
import com.example.vehicletickettermproject.data.Reservation
import com.example.vehicletickettermproject.data.VTUser
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class HomeViewModel : ViewModel(){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _user = MutableStateFlow<VTUser?>(null)
    val user: StateFlow<VTUser?> get() = _user

    private val _busJourneys = MutableStateFlow<List<BusJourney>>(emptyList())
    val busJourneys: StateFlow<List<BusJourney>> get() = _busJourneys

    // this is essentially pastTravels + upcomingTravels
    private val _reservations = MutableStateFlow<List<Pair<Reservation, BusJourney>>>(emptyList())
    val reservations: StateFlow<List<Pair<Reservation, BusJourney>>> get() = _reservations


    private val _isUserDataLoading = MutableStateFlow(true)
    val isUserDataLoading: StateFlow<Boolean> get() = _isUserDataLoading

    // I admit, the naming is a bit confusing
    // these are about the buses while pastTravels and upcomingTravels() are about users travels
    // i dont want to change upcomingTravels' name to upcomingReservations to keep consistent with pastX, upcomingX
    private val _upcomingBusJourneys = MutableStateFlow<List<BusJourney>>(emptyList())
    val upcomingBusJourneys: StateFlow<List<BusJourney>> get() = _upcomingBusJourneys

    // probably wont be used but anyways
    private val _pastBusJourneys = MutableStateFlow<List<BusJourney>>(emptyList())
    val pastBusJourneys: StateFlow<List<BusJourney>> get() = _pastBusJourneys


    private val _pastTravels = MutableStateFlow<List<Pair<Reservation, BusJourney>>>(emptyList())
    val pastTravels: StateFlow<List<Pair<Reservation, BusJourney>>> get() = _pastTravels

    private val _upcomingTravels = MutableStateFlow<List<Pair<Reservation, BusJourney>>>(emptyList())
    val upcomingTravels: StateFlow<List<Pair<Reservation, BusJourney>>> get() = _upcomingTravels

    private val _fromPlace = MutableStateFlow<String?>(null)
    val fromPlace: StateFlow<String?> get() = _fromPlace

    private val _toPlace = MutableStateFlow<String?>(null)
    val toPlace: StateFlow<String?> get() = _toPlace

    private val _beginDate = MutableStateFlow<Date?>(null)
    val beginDate: StateFlow<Date?> get() = _beginDate

    // since we are small bus company, its easier to keep all potential destinations as constant
    val allPlaces = listOf("EDIRNE","KESAN","UZUNKOPRU","ISTANBUL","TEKIRDAG","KIRKLARELI")



    init {
        reInitialize()
    }


    fun updateFromPlace(place: String?) {
        _fromPlace.value = place
        updateFilteredJourneys()
    }

    fun updateToPlace(place: String?) {
        _toPlace.value = place
        updateFilteredJourneys()
    }

    fun updateBeginDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        _beginDate.value = calendar.time
        updateFilteredJourneys()
    }

    private fun fetchUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get().addOnSuccessListener { documentSnapshot ->

                // this creates a VTUser instance using the info from database
                if (documentSnapshot.exists()) {
                    _user.value = documentSnapshot.toObject(VTUser::class.java)
                    fetchReservations()
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
            updateBusJourneys()
            Log.d("HomeViewModel", "Fetched bus journeys: $journeys")
        }.addOnFailureListener { exception ->
            Log.e("HomeViewModel", "Error fetching bus journeys", exception)
        }
    }

    private fun fetchReservations() {
        val reservations = _user.value?.reservations ?: return
        viewModelScope.launch {
            val reservationDetails = reservations.mapNotNull { reservation ->
                val busJourney = firestore.collection("busJourney").document(reservation.busJourneyId).get().await().toObject(BusJourney::class.java)
                busJourney?.let { reservation to it }
            }
            _reservations.value = reservationDetails
            updateTravels()
        }
    }

    internal fun reInitialize(){
        fetchUserData()
        fetchBusJourneyData()
    }

    internal fun clearState() {
        _user.value = null
        _reservations.value = emptyList()
        _busJourneys.value = emptyList()
        _isUserDataLoading.value = true
        _pastBusJourneys.value = emptyList()
        _upcomingBusJourneys.value = emptyList()
        _pastTravels.value = emptyList()
        _upcomingTravels.value = emptyList()
    }

    private fun updateBusJourneys() {
        val currentTime = Date()
        val (past, upcoming) = _busJourneys.value.partition { it.beginDateTime?.toDate()?.before(currentTime) == true }
        _pastBusJourneys.value = past
        _upcomingBusJourneys.value = upcoming
    }

    private fun updateTravels() {
        val currentTime = Date()
        val (past, upcoming) = _reservations.value.partition { it.second.beginDateTime?.toDate()?.before(currentTime) == true }
        _pastTravels.value = past
        _upcomingTravels.value = upcoming
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        return SimpleDateFormat("yyyyMMdd").format(date1) == SimpleDateFormat("yyyyMMdd").format(date2)
    }

    private fun updateFilteredJourneys() {
        val filteredJourneys = busJourneys.value.filter{ it.beginDateTime?.toDate()?.before(Date()) == false }
            .filter { journey ->
            val matchesFromPlace = _fromPlace.value?.let { it == journey.fromPlace } ?: true
            val matchesToPlace = _toPlace.value?.let { it == journey.toPlace } ?: true
                val matchesBeginDate = _beginDate.value?.let { selectedDate ->
                    journey.beginDateTime?.toDate()?.let { isSameDay(it, selectedDate) } ?: false
                } ?: true

            matchesFromPlace && matchesToPlace && matchesBeginDate
        }.sortedBy { it.beginDateTime?.toDate() }
        _upcomingBusJourneys.value = filteredJourneys
    }

    fun clearFilters() {
        _fromPlace.value = null
        _toPlace.value = null
        _beginDate.value = null
        updateFilteredJourneys()
    }

}