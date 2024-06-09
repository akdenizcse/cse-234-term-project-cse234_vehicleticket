@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.vehicletickettermproject.screens.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.screens.signup.CustomTextField2

@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel = viewModel()) {
    val email by signInViewModel.email.collectAsState()
    val password by signInViewModel.password.collectAsState()
    val isLoading by signInViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val backgroundColor = colorResource(id = R.color.secondary)
    val lemonadaFamily = FontFamily(
        Font(R.font.lemonada_semibold),
        Font(R.font.lemonada)
    )



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
                .height(210.dp)
                .width(200.dp)
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
        ){
            Text(
                text = "TOURISM",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = lemonadaFamily,
                color = Color(0xFFD32F2F)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))


        CustomTextField(
            value = email,
            onValueChange = { signInViewModel.onEmailChange(it) },
            label = "Email"
        )

        CustomTextField(
            value = password,
            onValueChange = { signInViewModel.onPasswordChange(it) },
            label = "Password",
            isPassword = true
        )





        /*OutlinedTextField(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 8.dp),
            value = email,
            colors =OutlinedTextFieldDefaults.colors(),
            onValueChange = { signInViewModel.onEmailChange(it)},
            label = { Text(
                text = "Email",
                color = Color(0xFF80D24545))
            }

        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 16.dp),
            value = password,
            colors = OutlinedTextFieldDefaults.colors(disabledContainerColor = Color(0xFFE4DEBE)),
            onValueChange = {signInViewModel.onPasswordChange(it)},
            label = { Text("Password",
                color = Color(0xFF80D24545)) },
            visualTransformation = PasswordVisualTransformation()
        )*/
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .height(35.dp)
                .width(120.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                signInViewModel.signIn(navController as NavHostController,
                    onError = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    })
            },
            enabled = !isLoading
        ) {
            Text(text = "Sign in", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString.Builder().apply {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFFD24545)
                    )
                ) {
                    append("Don't have an account? ")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFFD24545)
                    )
                ) {
                    append("Sign Up!")
                }
            }.toAnnotatedString(),
            onClick = { offset ->
                navController.navigate(VehicleTicketScreens.signup.name)
            }
        )

    }
}

@Composable
fun CustomTextField(label: String, isPassword: Boolean = false,value: String,
                    onValueChange: (String) -> Unit,) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(67.dp)
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
                focusedTextColor = colorResource(id = R.color.alternative_2),
                unfocusedTextColor = colorResource(id = R.color.alternative_2)

            ),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//





