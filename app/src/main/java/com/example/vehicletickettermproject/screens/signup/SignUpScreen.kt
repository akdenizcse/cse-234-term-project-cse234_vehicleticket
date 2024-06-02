@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.vehicletickettermproject.screens.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R


@Composable
fun SignUpScreen(navController: NavController,signUpViewModel: SignUpViewModel = viewModel()) {
    val firstname by signUpViewModel.firstname.collectAsState()
    val lastname by signUpViewModel.lastname.collectAsState()
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val isLoading by signUpViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val backgroundColor = colorResource(id = R.color.secondary)
    val alternativeColor = colorResource(id = R.color.primary)
    val lemonadaFamily = FontFamily(
        Font(R.font.lemonada_semibold),
        Font(R.font.lemonada)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.bus_icon), // Replace with your actual drawable resource
                contentDescription = null,
                modifier = Modifier
                    .height(173.dp)
                    .width(150.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )


            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "UZUNKOPRU",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = lemonadaFamily,
                    color = Color(0xFFD32F2F)

                )
            }
            Spacer(modifier = Modifier)

            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "TOURISM",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = lemonadaFamily,
                    color = Color(0xFFD32F2F)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField2(
                label = "First name",
                value = firstname,
                onValueChange = { signUpViewModel.onFirstnameChange(it) }

            )

            Spacer(modifier = Modifier.height(2.dp))

            CustomTextField2(
                label = "Last name",
                value = lastname,
                onValueChange = { signUpViewModel.onLastnameChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            CustomTextField2(
                label = "Email",
                value = email,
                onValueChange = { signUpViewModel.onEmailChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            CustomTextField2(
                label = "Password",
                value = password,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))



            Button(
                modifier = Modifier
                    .height(35.dp)
                    .width(120.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    signUpViewModel.signUp(
                        navController,
                        onSuccess = {},
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = !isLoading
            )
            {
                Text("Sign up", color = Color.White)
            }
        }

        IconButton(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.TopStart),
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                tint = colorResource(id = R.color.primary)

            )
        }
    }
}



@Composable
fun CustomTextField2(label: String,value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(68.dp)
            .padding(vertical = 8.dp)
            .background(Color(0xFFF5F0C6), shape = RoundedCornerShape(10.dp))

    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color(0xFFD99C9C), fontSize = 12.sp) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.secondary),
                focusedTextColor = colorResource(id = R.color.alternative_2)

            ),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}





