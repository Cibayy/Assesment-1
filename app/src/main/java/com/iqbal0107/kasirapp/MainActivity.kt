package com.iqbal0107.kasirapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.iqbal0107.kasirapp.ui.screen.InputScreen
import com.iqbal0107.kasirapp.ui.screen.ResultScreen
import com.iqbal0107.kasirapp.ui.theme.KasirAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KasirAppTheme {
                KasirApp()
            }
        }
    }
}

@Composable
fun KasirApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "input"
    ) {

        composable("input") {
            InputScreen(navController)
        }

        composable(
            "result/{nama}/{total}/{bayar}/{kembalian}/{pajak}"
        ) { backStackEntry ->

            ResultScreen(
                nama = backStackEntry.arguments?.getString("nama") ?: "",
                total = backStackEntry.arguments?.getString("total") ?: "0",
                bayar = backStackEntry.arguments?.getString("bayar") ?: "0",
                kembalian = backStackEntry.arguments?.getString("kembalian") ?: "0",
                pajak = backStackEntry.arguments?.getString("pajak") ?: "0",
                navController = navController
            )
        }
    }
}