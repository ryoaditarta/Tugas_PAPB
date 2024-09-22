package com.tifd.projectcomposed

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import com.tifd.projectcomposed.ui.theme.ProjectComposeDTheme

//private val LightColorScheme = lightColorScheme(
//    background = Color.White
//)
//
//@Composable
//fun ProjectComposeDTheme(
//    content: @Composable () -> Unit
//) {
//    MaterialTheme(
//        colorScheme = LightColorScheme,
//        content = content
//    )
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectComposeDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
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
    val DodgeBlue = Color(0xFF1E90FF)
    var submittedList by remember { mutableStateOf("")}

//    fitur baru
    var sortMenuExpanded by remember { mutableStateOf(false) }
    var filterMenuExpanded by remember { mutableStateOf(false) }

    var selectedSortOption by remember { mutableStateOf("Sort by") }
    var selectedFilterOption by remember { mutableStateOf("Filter by") }

    val sortOptions = listOf("Alpha ASC", "Alpha DSC", "NIM ASC", "NIM DSC", "ALL")
    val filterOptions = listOf("21", "22", "ALL")

    // Upload
    var showTextField by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                Text(text = "${student.Nama}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${student.NIM}", style = MaterialTheme.typography.bodyMedium)
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


    fun sortStudent(list: List<Student>, sortOption: String): List<Student> {
        return when (sortOption) {
            "Alpha ASC" -> list.sortedBy { it.Nama }
            "Alpha DSC" -> list.sortedByDescending { it.Nama }
            "NIM ASC" -> list.sortedBy { it.NIM }
            "NIM DSC" -> list.sortedByDescending { it.NIM }
            else -> list
        }
    }

    fun filterStudent(list: List<Student>, query: String): List<Student> {
        val sttemp = mutableListOf<Student>()

        if (query == "Filter by" || query == "ALL") {
            return list
        } else {
            for (x in list) {
                if (x.NIM.startsWith(query)) {
                    sttemp.add(x)
                }
            }
        }
        return sttemp
    }

    @Composable
    fun renderTextField(){
        OutlinedTextField(
            value = submittedList,
            onValueChange = { submittedList = it },
            label = { Text("Nama,NIM") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(300.dp).height(150.dp).verticalScroll(scrollState),
            textStyle = TextStyle(
                color = Color.Black
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(onClick = {
                try {
                    val temp = submittedList.split(",")
                    for (i in temp.indices step 2) {
                        if (i + 1 < temp.size) {
                            listStudent = listStudent + Student(temp[i], temp[i + 1])
                        }
                    }
                    showTextField = !showTextField
                } catch (e: Exception) {
                    // Handle exception
                    errorMessage = "Format salah"
                }
            }, enabled = Nama.isNotEmpty() && NIM.isNotEmpty()) {
                Text("Add")
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(DodgeBlue)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Mahasiswa Manager",
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Icon Profile",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = Nama,
                    onValueChange = { Nama = it },
                    label = { Text("Masukkan nama") },
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(
                        color = Color.Black
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "NIM",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = NIM,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            NIM = it
                        }
                    },
                    label = { Text("NIM") },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        showTextField = !showTextField
                    }
                ){
                    Text("Upload")
                }

                Button(onClick = {
                    if (Nama.isEmpty() || NIM.isEmpty() || listStudent.any { it.NIM == NIM }) {
                        errorMessage = "Isi data dengan lengkap/Data Sudah ada"
                    } else {
                        listStudent = listStudent + Student(Nama, NIM)
                        Nama = ""
                        NIM = ""
                        errorMessage = null
                    }
                }, enabled = Nama.isNotEmpty() && NIM.isNotEmpty()) {
                    Text("Add")
                }
            }

            errorMessage?.let { message ->
                Text(text = message, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            if (showTextField) {
                renderTextField()
            }

            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box {
                    ClickableText(
                        text = AnnotatedString(selectedSortOption),
                        onClick = { sortMenuExpanded = true },
                        modifier = Modifier.padding(8.dp)
                    )

                    DropdownMenu(
                        expanded = sortMenuExpanded,
                        onDismissRequest = { sortMenuExpanded = false }
                    ) {
                        sortOptions.forEach { sortOption ->
                            DropdownMenuItem(
                                text = { Text(sortOption) },
                                onClick = {
                                    selectedSortOption = sortOption
                                    sortMenuExpanded = false
                                }
                            )
                        }
                    }
                }

//                 Filter Dropdown
                Box {
                    ClickableText(
                        text = AnnotatedString(selectedFilterOption),
                        onClick = { filterMenuExpanded = true },
                        modifier = Modifier.padding(8.dp)
                    )

                    DropdownMenu(
                        expanded = filterMenuExpanded,
                        onDismissRequest = { filterMenuExpanded = false }
                    ) {
                        filterOptions.forEach { filterOption ->
                            DropdownMenuItem(
                                text = { Text(filterOption) },
                                onClick = {
                                    selectedFilterOption = filterOption
                                    filterMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var sortedList = sortStudent(listStudent, selectedSortOption)
                var filteredList = filterStudent(sortedList, selectedFilterOption)
//                listStudent = filteredList
                items(filteredList, key = { it.NIM }) { student ->
                    StudentCard(student) { studentToRemove ->
                        listStudent = listStudent.filter { it != studentToRemove }
                    }
                }
            }
        }
    }
}