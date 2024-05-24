package com.example.vehicletickettermproject.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun SignUpScreen(navController: NavController,signUpViewModel: SignUpViewModel = viewModel()){
    val firstname by signUpViewModel.firstname.collectAsState()
    val lastname by signUpViewModel.lastname.collectAsState()
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val isLoading by signUpViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        TextField(
            value = firstname,
            onValueChange = { signUpViewModel.onFirstnameChange(it) },
            label = { Text(text = "First Name") })

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastname,
            onValueChange = { signUpViewModel.onLastnameChange(it) },
            label = { Text(text = "Last Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { signUpViewModel.onEmailChange(it)},
            label = { Text(text = "Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = {signUpViewModel.onPasswordChange(it)},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {signUpViewModel.signUp(
                navController,
                onSuccess = {},
                onError = {message -> Toast.makeText(context,message, Toast.LENGTH_LONG).show() }
            )},
            enabled = !isLoading
        )
        {
            Text("Sign up")
        }

    }


}