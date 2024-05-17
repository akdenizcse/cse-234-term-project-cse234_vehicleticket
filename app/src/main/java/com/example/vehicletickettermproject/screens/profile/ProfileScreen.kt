package com.example.vehicletickettermproject.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@Composable
fun ProfileScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    Text(text = "profile")
}