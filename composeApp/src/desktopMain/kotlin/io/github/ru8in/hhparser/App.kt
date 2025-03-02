package io.github.ru8in.hhparser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
@Preview
fun App() {
    val logger = LoggerFactory.getLogger("App")

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