package io.github.ru8in.hhparser.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Paths


class HeadHunterAPIClient {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        defaultRequest {
            url("https://api.hh.ru/")
            headers {
                append("User-Agent", "MyApp/1.0 (iskatel.03@mail.ru)")
            }
        }
        install(HttpCache) {
            val cacheFile = Files.createDirectories(Paths.get("tmp/cache")).toFile()
            publicStorage(FileStorage(cacheFile))
        }

    }
//        install(Auth) {
//            bearer {
//
//            }
//        }

    suspend fun auth(password: String) {

    }


    suspend fun make(): String {
        val resp = this.client.get("https://google.com")
        return resp.body()
    }
}

fun main(): Unit = runBlocking {

    val client = HeadHunterAPIClient()
    println(client.make())

}