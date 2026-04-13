package com.iqbal0107.kasirapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iqbal0107.kasirapp.Item
import com.iqbal0107.kasirapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavHostController) {

    var nama by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }

    var listBarang by remember { mutableStateOf(listOf<Item>()) }

    var metode by remember { mutableStateOf("") }
    var bayar by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    val subtotal = listBarang.sumOf { it.harga * it.jumlah }
    val pajak = subtotal * 0.1
    val total = subtotal + pajak

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Kasir App") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Image(
                    painter = painterResource(id = R.drawable.logo_toko),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            // 🔹 INPUT
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

            // 🔥 TAMBAH BARANG
            item {
                Button(
                    onClick = {
                        try {
                            val h = harga.toDouble()
                            val j = jumlah.toDouble()

                            listBarang = listBarang + Item(nama, h, j)

                            nama = ""
                            harga = ""
                            jumlah = ""
                            errorMessage = ""

                        } catch (e: Exception) {
                            errorMessage = "Input tidak valid!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tambah Barang")
                }
            }

            // 🔥 LIST BARANG
            items(listBarang) { item ->
                Text("${item.nama} - ${item.harga} x ${item.jumlah}")
            }

            // 🔥 TOTAL
            if (listBarang.isNotEmpty()) {
                item {
                    Text("Subtotal: $subtotal")
                    Text("Pajak: $pajak")
                    Text("Total: $total")
                }

                // 🔹 METODE
                item {
                    Text("Metode Pembayaran")

                    Row {
                        RadioButton(
                            selected = metode == "Cash",
                            onClick = { metode = "Cash" }
                        )
                        Text("Cash")

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = metode == "QRIS",
                            onClick = { metode = "QRIS" }
                        )
                        Text("QRIS")
                    }
                }

                // 🔹 CASH
                if (metode == "Cash") {
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
                                    val b = bayar.toDouble()
                                    if (b < total) {
                                        errorMessage = "Uang kurang!"
                                        return@Button
                                    }

                                    val kembalian = b - total

                                    navController.navigate(
                                        "result/MultiItem/$total/$b/$kembalian/$pajak"
                                    )

                                } catch (e: Exception) {
                                    errorMessage = "Input salah!"
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Bayar")
                        }
                    }
                }

                // 🔥 QRIS
                if (metode == "QRIS") {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.qris),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                navController.navigate(
                                    "result/MultiItem/$total/$total/0/$pajak"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Selesai (QRIS)")
                        }
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                item {
                    Text(errorMessage)
                }
            }
        }
    }
}