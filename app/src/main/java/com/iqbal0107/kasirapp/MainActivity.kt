package com.iqbal0107.kasirapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.iqbal0107.kasirapp.ui.screen.AboutScreen
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

        // 🔹 INPUT SCREEN
        composable("input") {
            InputScreen(navController)
        }

        // 🔹 ABOUT SCREEN (INI YANG TADI KURANG)
        composable("about") {
            AboutScreen(navController)
        }

        // 🔹 RESULT SCREEN
        composable(
            route = "result/{nama}/{total}/{bayar}/{kembalian}/{pajak}"
        ) { backStackEntry ->

            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val total = backStackEntry.arguments?.getString("total") ?: "0"
            val bayar = backStackEntry.arguments?.getString("bayar") ?: "0"
            val kembalian = backStackEntry.arguments?.getString("kembalian") ?: "0"
            val pajak = backStackEntry.arguments?.getString("pajak") ?: "0"

            ResultScreen(
                nama = nama,
                total = total,
                bayar = bayar,
                kembalian = kembalian,
                pajak = pajak,
                navController = navController
            )
        }
    }
}