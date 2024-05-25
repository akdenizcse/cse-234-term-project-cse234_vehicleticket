package com.example.vehicletickettermproject.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.screens.home.HomeViewModel
import com.example.vehicletickettermproject.screens.signin.SignInViewModel

@Composable
fun BottomNavigationBar(navController: NavController, signInViewModel: SignInViewModel,homeViewModel: HomeViewModel){
    val items = listOf(
        VehicleTicketScreens.search,
        VehicleTicketScreens.reservations,
        VehicleTicketScreens.pastTravels,
    )



    BottomNavigation {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach {screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.name,
                onClick = {
                    if (screen == VehicleTicketScreens.logout) {
                        homeViewModel.clearState()
                        signInViewModel.signOut(navController)
                    }
                    else{
                        navController.navigate(screen.name) {


                            // this avoids multiple copies of the same destination when reselecting the same item
                            launchSingleTop = true

                        }
                    }
                }
                , icon = {
                    when(screen) {
                        VehicleTicketScreens.search -> Icon(Icons.Default.Home,contentDescription = "Home")
                        VehicleTicketScreens.reservations -> Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Reservations")
                        VehicleTicketScreens.pastTravels -> Icon(Icons.Default.Person, contentDescription = "Profile")
                        VehicleTicketScreens.logout -> Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                        else -> {}
                    }

                })
        }
    }

}