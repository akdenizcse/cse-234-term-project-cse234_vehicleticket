package com.example.vehicletickettermproject.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
        VehicleTicketScreens.home,
        VehicleTicketScreens.reservations,
        VehicleTicketScreens.profile,
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
                    val iconTint = if (currentRoute == screen.name) Color.Gray else Color.White

                    when(screen) {
                        VehicleTicketScreens.home -> Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Home",
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                        VehicleTicketScreens.reservations -> Icon(
                            painter = painterResource(id = R.drawable.reservations), // Replace with your ticket icon
                            contentDescription = "Ticket",
                            tint = iconTint,
                            modifier = Modifier.size(24.dp) // Adjust the size as needed
                        )
                        VehicleTicketScreens.profile -> Icon(
                            painter = painterResource(id = R.drawable.past_travels),
                            contentDescription = "Profile",
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                        else -> {}
                    }
                }
            )
        }
    }

}