package com.example.vehicletickettermproject.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.screens.home.HomeViewModel
import com.example.vehicletickettermproject.screens.signin.SignInViewModel
import com.example.vehicletickettermproject.R


@Composable
fun BottomNavigationBar(navController: NavController, signInViewModel: SignInViewModel,homeViewModel: HomeViewModel){
    val items = listOf(
        VehicleTicketScreens.search,
        VehicleTicketScreens.reservations,
        VehicleTicketScreens.pastTravels,
    )



    BottomNavigation(
        backgroundColor = Color(0xFFD24545),
        contentColor = Color.White,
        modifier = Modifier.height(56.dp)
    ){

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach {screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        // this avoids multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                    }
                }
                , icon = {
                    when(screen) {
                        VehicleTicketScreens.search -> Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        VehicleTicketScreens.reservations -> Icon(
                            painter = painterResource(id = R.drawable.reservations), // Replace with your ticket icon
                            contentDescription = "Ticket",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp) // Adjust the size as needed
                        )
                        VehicleTicketScreens.pastTravels -> Icon(
                            painter = painterResource(id = R.drawable.past_travels),
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        VehicleTicketScreens.logout -> Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                        else -> {}
                    }

                })
        }
    }

}