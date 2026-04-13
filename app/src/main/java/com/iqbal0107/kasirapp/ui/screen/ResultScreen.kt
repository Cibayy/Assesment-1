package com.iqbal0107.kasirapp.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    nama: String,
    total: String,
    bayar: String,
    kembalian: String,
    pajak: String,
    navController: NavHostController
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Struk Pembayaran") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("===== STRUK =====")
            Spacer(modifier = Modifier.height(10.dp))

            Text("Barang: $nama")
            Text("Total: $total")
            Text("Pajak: $pajak")
            Text("Bayar: $bayar")
            Text("Kembalian: $kembalian")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "STRUK\nBarang: $nama\nTotal: $total\nBayar: $bayar\nKembalian: $kembalian"
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kembali")
            }
        }
    }
}