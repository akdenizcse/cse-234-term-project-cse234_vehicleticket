package com.example.vehicletickettermproject

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vehicletickettermproject.components.BottomNavigationBar
import com.example.vehicletickettermproject.screens.home.HomeScreen
import com.example.vehicletickettermproject.screens.home.HomeViewModel
import com.example.vehicletickettermproject.screens.journeydetails.JourneyDetailsScreen
import com.example.vehicletickettermproject.screens.payment.PaymentScreen
import com.example.vehicletickettermproject.screens.profile.ProfileScreen
import com.example.vehicletickettermproject.screens.reservations.ReservationsScreen
import com.example.vehicletickettermproject.screens.signin.SignInScreen
import com.example.vehicletickettermproject.screens.signin.SignInViewModel
import com.example.vehicletickettermproject.screens.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


enum class VehicleTicketScreens(){
    signin,
    signup,
    search,
    reservations,
    pastTravels,
    logout,
    journeydetails,
    payment
}


@Composable
fun VehicleTicketApp() {
    val navController = rememberNavController()
    val firebaseAuth = FirebaseAuth.getInstance()
    val signInViewModel: SignInViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    var isAuthenticated by remember { mutableStateOf(firebaseAuth.currentUser != null) }

    LaunchedEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->

            isAuthenticated = auth.currentUser != null
            if(!isAuthenticated){
                homeViewModel.clearState()
                homeViewModel.clearFilters()
                Log.d("authchange", "authchanged to: $isAuthenticated")
            }
            else{
                Log.d("authchange", "authchanged to: $isAuthenticated")
                homeViewModel.reInitialize()
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }


    Scaffold(
        bottomBar = {
            if (isAuthenticated) {
                BottomNavigationBar(navController = navController,signInViewModel,homeViewModel)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isAuthenticated) VehicleTicketScreens.search.name else VehicleTicketScreens.signin.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(VehicleTicketScreens.signin.name) {
                SignInScreen(navController = navController,signInViewModel = signInViewModel)
            }
            composable(VehicleTicketScreens.signup.name) {
                SignUpScreen(navController = navController)
            }
            composable(VehicleTicketScreens.search.name) {
                HomeScreen(navController = navController,homeViewModel)
            }
            composable(VehicleTicketScreens.reservations.name) {
                ReservationsScreen(navController = navController,homeViewModel)
            }
            composable(VehicleTicketScreens.pastTravels.name) {
                ProfileScreen(navController = navController,homeViewModel)
            }
            composable(
                route = "${VehicleTicketScreens.journeydetails.name}/{journeyId}",
                arguments = listOf(navArgument("journeyId") { type = NavType.StringType })
            ) { backStackEntry ->
                val journeyId = backStackEntry.arguments?.getString("journeyId") ?: ""
                JourneyDetailsScreen(navController = navController, journeyId = journeyId, homeViewModel = homeViewModel)
            }
            composable(
                route = "${VehicleTicketScreens.payment.name}/{journeyId}/{seatNumber}",
                arguments = listOf(
                    navArgument("journeyId") { type = NavType.StringType },
                    navArgument("seatNumber") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val journeyId = backStackEntry.arguments?.getString("journeyId") ?: ""
                val seatNumber = backStackEntry.arguments?.getString("seatNumber") ?: ""
                PaymentScreen(navController = navController, journeyId = journeyId, selectedSeat = seatNumber,homeViewModel = homeViewModel)
            }
        }
    }
}


