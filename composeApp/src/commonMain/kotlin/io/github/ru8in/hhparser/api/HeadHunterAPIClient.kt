package io.github.ru8in.hhparser.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class HeadHunterAPIClient {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
//        install(Auth) {
//            bearer {
//
//            }
//        }
    }

    suspend fun make(): String {
        val resp = this.client.get("https://google.com")
        return resp.body()
    }


}