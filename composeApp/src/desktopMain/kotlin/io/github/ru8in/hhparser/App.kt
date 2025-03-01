package io.github.ru8in.hhparser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.ru8in.hhparser.api.HeadHunterAPIClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showCountries by remember { mutableStateOf(false) }
        var timeAtLocation by remember { mutableStateOf("No Location Selected") }

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            HelloContent()
            Text(timeAtLocation,
                style = TextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

            Row(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp).align(Alignment.CenterHorizontally),
            ) {
                DropdownMenu(
                    expanded = showCountries,
                    onDismissRequest = { showCountries = false },
                ) {
                    countries().forEach { (name, zone) ->
                        DropdownMenuItem(
                            onClick = {
                                timeAtLocation = currentTimeAt(zone.id) ?: "Invalid location"
                                showCountries = false
                            }
                        ) { Text(name) }
                    }
                }
            }
            Button(
                onClick = { showCountries = !showCountries },
                modifier = Modifier.padding(start = 20.dp, top = 10.dp).align(Alignment.CenterHorizontally)) {
                Text("Show Time At Location")
            }
        }
    }
}

@Composable
fun HelloContent() {

    val client = HeadHunterAPIClient()

    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
    Column(modifier = Modifier.padding(16.dp)) {
        var resp by remember { mutableStateOf("") }
        val scope =  rememberCoroutineScope()

        if (resp.isNotEmpty()) {
            Text(
                text = resp,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        Button(
            onClick = { scope.launch { resp = client.make() }}
        ) {
            Text("Make Request")
        }
    }
}


data class Country(val name: String, val zoneId: ZoneId)

fun countries() = listOf(
    Country("Japan", ZoneId.of("Asia/Tokyo")),
    Country("France", ZoneId.of("Europe/Paris")),
    Country("Mexico", ZoneId.of("America/Mexico_City")),
    Country("Indonesia", ZoneId.of("Asia/Jakarta")),
    Country("Egypt", ZoneId.of("Africa/Cairo")),
)

fun currentTimeAt(location: String): String? {
    return try {
        val lt = ZonedDateTime.now(ZoneId.of(location))
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        "The time is ${lt.format(formatter)}"
    } catch (ex: Exception) {
        println(ex.toString())
        null
    }
}