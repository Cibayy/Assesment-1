package com.iqbal0107.kasirapp.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ResultScreen(
    nama: String,
    total: String,
    bayar: String,
    kembalian: String,
    pajak: String,
    navController: NavHostController
) {
    Text("Ini halaman hasil")
}