package com.example.projetopokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetopokedex.ui.login.LoginScreen
import com.example.projetopokedex.ui.signup.SignUpScreen
import com.example.projetopokedex.ui.navigation.PokedexScreen
import com.example.projetopokedex.ui.theme.ProjetoPokedexTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoPokedexTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = PokedexScreen.Login.name
                ) {
                    composable(PokedexScreen.Login.name) {
                        LoginScreen(
                            onNavigateToSignUp = {
                                navController.navigate(PokedexScreen.SignUp.name)
                            }
                        )
                    }
                    composable(PokedexScreen.SignUp.name) {
                        SignUpScreen(
                            onBackToLogin = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}