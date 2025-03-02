package io.github.ru8in.hhparser.api

import io.github.ru8in.hhparser.api.models.vacancy.*
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.slf4j.LoggerFactory
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Properties
import kotlin.math.log


class HeadHunterAPIClient {

    private val bearerTokenStorage = mutableListOf<BearerTokens>()
    private val properties = Properties()

    private val client: HttpClient
    private val logger = LoggerFactory.getLogger(HeadHunterAPIClient::class.java.canonicalName)

    init {
        readProperties()

        if (this.properties.getProperty("access_token") != null || this.properties.getProperty("refresh_token") != null) {
            bearerTokenStorage.add(BearerTokens(this.properties.getProperty("access_token"), this.properties.getProperty("refresh_token")))
        }

        client = HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.INFO
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url("https://api.hh.ru/")
                headers {
                    append("User-Agent", "KtlApp/1.0 (iskatel.03@mail.ru)")
                }
            }
            install(HttpCache) {
                val cacheFile = Files.createDirectories(Paths.get("tmp/cache.txt")).toFile()
                publicStorage(FileStorage(cacheFile))
            }
            install(Auth) {
                bearer {
                    loadTokens { bearerTokenStorage.lastOrNull() }
                    refreshTokens {
                        val refreshTokenInfo: TokenInfo = this.client.post("token") {
                            setBody(FormDataContent(Parameters.build {
                                append("grant_type", "authorization_code")
                                append("code", properties.getProperty("authorization_code"))
                                append("client_id", properties.getProperty("client_id"))
                                append("client_secret", properties.getProperty("client_secret"))
                                append("redirect_uri", "https://example.com")
                            }))
                            headers { append("Content-Type", "application/x-www-form-urlencoded") }
                            markAsRefreshTokenRequest()
                        }.body()
                        bearerTokenStorage.add(BearerTokens(refreshTokenInfo.accessToken, refreshTokenInfo.refreshToken!!))
                        saveProperties()
                        bearerTokenStorage.last()

                    }
                    sendWithoutRequest { request ->
                        request.url.host == "api.hh.ru"
                    }
                }
            }
        }
    }

    /**
     * Gets the URL that the user should open in their browser to grant
     * the application access to their HeadHunter account.
     *
     * The user should open this URL, grant the application access, and
     * then give the authorization code to the application. The
     * application can then use this authorization code to get a
     * refresh token.
     *
     * @return the URL that the user should open in their browser.
     */
    fun getClientCodeURL(): String {
        try {
            return "https://hh.ru/oauth/authorize?response_type=code&client_id=${properties.getProperty("client_id")}&scope=vacancy&redirect_uri=https://example.com"
        } catch (ex: NullPointerException) {
            logger.error("Missing configuration property: ${ex.message}")
            throw ex
        }
    }

    /**
     * Read the configuration properties from a file named "config.ini".
     * The properties are the authorization code, client ID, and client secret.
     */
    private fun readProperties() {
        try {
            this.properties.load(InputStreamReader(FileInputStream("config.ini")))
            properties.getProperty("authorization_code")!!
            properties.getProperty("client_id")!!
            properties.getProperty("client_secret")!!
        } catch (ex: NullPointerException) {
            logger.error("Missing configuration property: ${ex.message}")
            throw ex
        } catch (ex: FileNotFoundException) {
            logger.error("Configuration file not found: ${ex.message}")
            throw ex
        }
    }

    /**
     * Saves the current configuration properties to a file named "config.ini".
     */
    private fun saveProperties() {
        if (bearerTokenStorage.isNotEmpty()) {
            properties["access_token"] = bearerTokenStorage.last().accessToken
            properties["refresh_token"] = bearerTokenStorage.last().refreshToken
            logger.info("Added access_token and refresh_token to properties")
        }
        this.properties.store(OutputStreamWriter(FileOutputStream("config.ini")), null)
        logger.info("Saved config.ini")
    }

    suspend fun getVacancy(id: Int): Vacancy {
        val vacancy: Vacancy = client.get("vacancies/$id").body()
        logger.info(vacancy.toString())
        return vacancy
    }

    suspend fun getVacancies(vacancyRequest: VacanciesRequest): List<Vacancy> {
        val result = mutableListOf<Vacancy>()
        var page = 0
        var totalPages = 1
        while (page < totalPages) {
            val response = client.get("vacancies") {
                setBody(vacancyRequest.copy(page = page))
                contentType(ContentType.Application.Json)
            }
            if (response.status.isSuccess()) {
                val payload: VacanciesResponse = response.body()
//                totalPages = payload.pages
                result.addAll(payload.items)
                page++
            } else {
                logger.error("Failed to get vacancies: ${response.status.value} ${response.status.description}")
                throw VacanciesErrorException(response.body())
            }
        }
        return result
    }

    suspend fun saveDictionaries() {
        if (!File("dictionaries.json").exists()) {
            File("dictionaries.json").writeText(client.get("dictionaries").body())
            logger.info("Saved dictionaries.json")
        }
        logger.info("Dictionaries already saved")
    }

}

fun main(): Unit = runBlocking {

    val client = HeadHunterAPIClient()

    val request = VacanciesRequest(0, 10, "Программист")
    println(client.getVacancies(request))
}