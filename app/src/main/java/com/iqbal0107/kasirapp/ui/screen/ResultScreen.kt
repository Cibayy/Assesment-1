package com.iqbal0107.kasirapp.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iqbal0107.kasirapp.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 🔥 FORMAT RUPIAH (FIX NO WARNING)
fun formatRupiah(angka: Double): String {
    val localeID = Locale.Builder()
        .setLanguage("id")
        .setRegion("ID")
        .build()

    val format = NumberFormat.getNumberInstance(localeID)
    format.maximumFractionDigits = 0

    return "Rp ${format.format(angka)}"
}

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

    // 🔥 TANGGAL OTOMATIS
    val localeID = Locale.Builder()
        .setLanguage("id")
        .setRegion("ID")
        .build()

    val currentDate = SimpleDateFormat(
        "dd MMM yyyy HH:mm",
        localeID
    ).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Struk") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5C6BC0),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Card(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_toko),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(Modifier.height(10.dp))

                Text("STRUK PEMBAYARAN", fontWeight = FontWeight.Bold)

                // 🔥 TANGGAL MUNCUL DI SINI
                Text(
                    "Tanggal: $currentDate",
                    style = MaterialTheme.typography.bodySmall
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                Text("Total: ${formatRupiah(total.toDouble())}")
                Text("Pajak: ${formatRupiah(pajak.toDouble())}")
                Text("Bayar: ${formatRupiah(bayar.toDouble())}")
                Text("Kembalian: ${formatRupiah(kembalian.toDouble())}")

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                Button(
                    onClick = {
                        val text = """
STRUK PEMBAYARAN
Tanggal: $currentDate

Total: ${formatRupiah(total.toDouble())}
Bayar: ${formatRupiah(bayar.toDouble())}
Kembalian: ${formatRupiah(kembalian.toDouble())}
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
            }
        }
    }
}