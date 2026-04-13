package com.iqbal0107.kasirapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.iqbal0107.kasirapp.R
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavHostController) {

    var nama by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }

    var metode by remember { mutableStateOf("") }
    var bayar by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var isCalculated by remember { mutableStateOf(false) }

    val subtotal = harga.toDoubleOrNull()?.times(jumlah.toDoubleOrNull() ?: 0.0) ?: 0.0
    val pajak = subtotal * 0.1
    val total = subtotal + pajak

    // 🔥 FORMAT TANPA FUNCTION GLOBAL (ANTI ERROR)
    val format = NumberFormat.getNumberInstance(Locale("in", "ID")).apply {
        maximumFractionDigits = 0
    }

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
                        Icon(Icons.Default.Info, contentDescription = "About")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

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

            Text("Input Barang", fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = harga,
                onValueChange = { harga = it },
                label = { Text("Harga") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = jumlah,
                onValueChange = { jumlah = it },
                label = { Text("Jumlah") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (
                        nama.isNotEmpty() &&
                        harga.toDoubleOrNull() != null &&
                        jumlah.toDoubleOrNull() != null
                    ) {
                        isCalculated = true
                        errorMessage = ""
                    } else {
                        errorMessage = "Isi data dengan benar!"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hitung Total")
            }

            if (isCalculated) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Ringkasan", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))

                        Text("Subtotal: Rp ${format.format(subtotal)}")
                        Text("Pajak (10%): Rp ${format.format(pajak)}")
                        Text("Total: Rp ${format.format(total)}", fontWeight = FontWeight.Bold)
                    }
                }

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

                if (metode == "Cash") {

                    OutlinedTextField(
                        value = bayar,
                        onValueChange = { bayar = it },
                        label = { Text("Uang Dibayar") },
                        modifier = Modifier.fillMaxWidth()
                    )

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
                                    "result/$nama/$total/$b/$kembalian/$pajak"
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

                if (metode == "QRIS") {

                    Image(
                        painter = painterResource(id = R.drawable.qris),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(
                                "result/$nama/$total/$total/0/$pajak"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Selesai (QRIS)")
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }
        }
    }
}