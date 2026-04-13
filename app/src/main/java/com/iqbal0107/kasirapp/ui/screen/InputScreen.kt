package com.iqbal0107.kasirapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavHostController) {

    var nama by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }
    var bayar by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kasir App") }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama Barang") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = harga,
                    onValueChange = { harga = it },
                    label = { Text("Harga") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = jumlah,
                    onValueChange = { jumlah = it },
                    label = { Text("Jumlah") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = bayar,
                    onValueChange = { bayar = it },
                    label = { Text("Uang Dibayar") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        try {
                            val h = harga.toDouble()
                            val j = jumlah.toDouble()
                            val b = bayar.toDouble()

                            val subtotal = h * j
                            val pajak = subtotal * 0.1
                            val total = subtotal + pajak
                            val kembalian = b - total

                            navController.navigate(
                                "result/$nama/$total/$b/$kembalian/$pajak"
                            )

                        } catch (e: Exception) {
                            error = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hitung")
                }
            }

            if (error) {
                item {
                    Text("Input tidak valid")
                }
            }
        }
    }
}