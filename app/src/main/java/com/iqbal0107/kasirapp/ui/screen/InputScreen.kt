package com.iqbal0107.kasirapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
            TopAppBar(
                title = { Text("Kasir App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5C6BC0),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate("about")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Tentang Aplikasi"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_toko),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            item {
                Text("Input Barang", fontWeight = FontWeight.Bold)
            }

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
                Button(
                    onClick = {
                        try {
                            listBarang = listBarang + Item(
                                nama,
                                harga.toDouble(),
                                jumlah.toDouble()
                            )

                            nama = ""
                            harga = ""
                            jumlah = ""
                            errorMessage = ""

                        } catch (e: Exception) {
                            errorMessage = "Input harus angka!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tambah Barang")
                }
            }

            if (listBarang.isNotEmpty()) {

                item {
                    Text("Daftar Barang", fontWeight = FontWeight.Bold)
                }

                itemsIndexed(listBarang) { index, item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${index + 1}. ${item.nama}", fontWeight = FontWeight.Bold)
                            Text("Harga: ${item.harga}")
                            Text("Jumlah: ${item.jumlah}")
                            Text("Total: ${item.harga * item.jumlah}")
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Ringkasan", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("Subtotal: $subtotal")
                            Text("Pajak (10%): $pajak")
                            Text("Total: $total", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                item {
                    Text("Metode Pembayaran", fontWeight = FontWeight.Bold)

                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                                        "result/Multi/$total/$b/$kembalian/$pajak"
                                    )

                                } catch (e: Exception) {
                                    errorMessage = "Input bayar salah!"
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Bayar")
                        }
                    }
                }

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
                                    "result/Multi/$total/$total/0/$pajak"
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