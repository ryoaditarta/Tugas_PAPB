package com.tifd.projectcomposed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tifd.projectcomposed.ui.theme.ProjectComposeDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectComposeDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

class Student(var Nama: String, var NIM: String)



@Composable
fun MyScreen() {
    var listStudent by remember { mutableStateOf(listOf<Student>()) }
    var Nama by remember { mutableStateOf("") }
    var NIM by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    @Composable
    fun StudentCard(student: Student, onRemove: (Student) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Name: ${student.Nama}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "NIM: ${student.NIM}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onRemove(student) },
                        modifier = Modifier
                            .padding(0.dp)
                            .size(width = 90.dp, height = 40.dp)
                    ) {
                        Text(text = "Remove", fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterVertically) )
                    }
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = Nama,
            onValueChange = { Nama = it },
            label = { Text("Nama") }
        )
        TextField(
            value = NIM,
            onValueChange = { NIM = it },
            label = { Text("NIM") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                if (Nama.isEmpty() || NIM.isEmpty()) {
                    errorMessage = "Isi data dengan lengkap"
                } else {
                    listStudent = listStudent + Student(Nama, NIM)
                    Nama = ""
                    NIM = ""
                    errorMessage = null
                }
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        errorMessage?.let { message ->
            Text(text = message, color = Color.Red, modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(listStudent, key = { it.NIM }) { student ->
                StudentCard(student) { studentToRemove ->
                    listStudent = listStudent.filter { it != studentToRemove }
                }
            }
        }
    }
}


