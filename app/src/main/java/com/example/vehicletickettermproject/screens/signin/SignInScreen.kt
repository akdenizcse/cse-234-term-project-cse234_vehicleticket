package com.example.vehicletickettermproject.screens.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.vehicletickettermproject.VehicleTicketScreens

@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel = viewModel()){
    val email by signInViewModel.email.collectAsState()
    val password by signInViewModel.password.collectAsState()
    val isLoading by signInViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = email,
            onValueChange = { signInViewModel.onEmailChange(it)},
            label = { Text(text = "Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = {signInViewModel.onPasswordChange(it)},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                signInViewModel.signIn(navController as NavHostController,
                onError = {message -> Toast.makeText(context,message, Toast.LENGTH_LONG).show() })},
            enabled = !isLoading
        ){
            Text(text = "Sign in")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString.Builder().apply {
                append("Don't have an account? ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline, color = Color.Blue)) {
                    append("Sign Up!")
                }
            }.toAnnotatedString(),
            onClick = { offset ->
                navController.navigate(VehicleTicketScreens.signup.name)
            }
        )

    }
}