package com.example.vehicletickettermproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vehicletickettermproject.screens.home.HomeScreen
import com.example.vehicletickettermproject.screens.signin.SignInScreen
import com.example.vehicletickettermproject.screens.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


enum class VehicleTicketScreens(){
    signin,
    signup,
    home
}


@Composable
fun VehicleTicketApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = VehicleTicketScreens.signin.name){
        composable(VehicleTicketScreens.signin.name){
            SignInScreen(navController = navController)
        }
        composable(VehicleTicketScreens.signup.name){
            SignUpScreen(navController = navController)
        }
        composable(VehicleTicketScreens.home.name){
            HomeScreen(navController = navController)
        }
    }

}


