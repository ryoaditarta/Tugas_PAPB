package com.tifd.projectcomposed

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tifd.projectcomposed.ui.theme.ProjectComposeDTheme

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectComposeDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    layarAdmin {
                        val intent = Intent(this@ListActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun layarAdmin(onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Selamat datang di layar admin!",
            color = Color.Black,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )

        Button(
            onClick = {
                onBackPressed()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Kembali ke Main Activity")
        }
    }
}
