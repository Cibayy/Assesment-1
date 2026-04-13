package com.iqbal0107.kasirapp.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iqbal0107.kasirapp.R

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

    val totalDouble = total.toDouble()
    val bayarDouble = bayar.toDouble()
    val kembalianDouble = kembalian.toDouble()
    val pajakDouble = pajak.toDouble()

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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 LOGO TOKO
            Image(
                painter = painterResource(id = R.drawable.logo_toko),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("===== STRUK PEMBAYARAN =====")

            Spacer(modifier = Modifier.height(10.dp))

            Text("Barang : $nama")
            Text("---------------------------")

            Text("Total   : ${formatRupiah(totalDouble)}")
            Text("Pajak   : ${formatRupiah(pajakDouble)}")
            Text("Bayar   : ${formatRupiah(bayarDouble)}")
            Text("Kembali : ${formatRupiah(kembalianDouble)}")

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 SHARE BUTTON
            Button(
                onClick = {
                    val text = """
                        STRUK PEMBAYARAN
                        Barang: $nama
                        Total: ${formatRupiah(totalDouble)}
                        Pajak: ${formatRupiah(pajakDouble)}
                        Bayar: ${formatRupiah(bayarDouble)}
                        Kembalian: ${formatRupiah(kembalianDouble)}
                    """.trimIndent()

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, text)

                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🔹 BACK BUTTON
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

// 🔥 FORMAT RUPIAH
fun formatRupiah(angka: Double): String {
    val format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("in", "ID"))
    return format.format(angka)
}