package com.example.danceclub.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.danceclub.ui.navigation.GreetingDestination
import com.example.danceclub.ui.navigation.ProfileDestination
import com.example.danceclub.ui.navigation.RegistrationDestination
import com.example.danceclub.ui.navigation.SignInDestination
import com.example.danceclub.ui.screens.auth.sign_up.RegistrationScreen
import com.example.danceclub.ui.screens.auth.sing_in.SignInScreen
import com.example.danceclub.ui.screens.greeting.GreetingScreen

@Composable
fun DanceAppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = GreetingDestination
    ) {
        composable<GreetingDestination> {
            GreetingScreen(
                onNavigateToSignIn = {
                    navController.navigate(route = SignInDestination)
                },
                onNavigateToRegistration = {
                    navController.navigate(route = RegistrationDestination)
                }
            )
        }
        composable<SignInDestination> {
            SignInScreen(
                onNavigateToProfile = {
                    navController.navigate(route = ProfileDestination) {
                        popUpTo<ProfileDestination> {
                            inclusive = true
                        }
                    }
                },
                onNavigateUpToGreeting = {
                    navController.navigate(route = GreetingDestination) {
                        popUpTo<GreetingDestination> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<RegistrationDestination> {
            RegistrationScreen(
                onNavigateToProfile = { // добавить передачу параметра phone
                    navController.navigate(route = ProfileDestination) {
                        popUpTo<ProfileDestination> {
                            inclusive = true
                        }
                    }
                },
                onNavigateUpToGreeting = {
                    navController.navigate(route = GreetingDestination) {
                        popUpTo<GreetingDestination> {
                            inclusive = true
                        }
                    }
                }
            )
        }
       /* composable<ProfileDestination> {
            ProfileScreen(
                onNavigateToTrainings = {
                    navController.navigate(route = TrainingsDestination)
                }
            )
        }
        composable<TrainingsDestination> {
            TrainingsScreen(
                onNavigateUpToProfile = {
                    navController.navigate(route = ProfileDestination) {
                        popUpTo<ProfileDestination> {
                            inclusive = true
                        }
                    }
                },
                onNavigateToDetail = { section: Training ->
                    val sectionJson = Json.encodeToString(section)
                    navController.navigate(route = "detail/$sectionJson")
                }
            )
        }*/
    }
}