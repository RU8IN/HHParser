package io.github.ru8in.hhparser.api.models.vacancy

import io.github.ru8in.hhparser.api.HeadHunterAPIClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Serializable
data class VacanciesRequest (
    var page: Int = 0,
    @SerialName("per_page") val perPage: Int = 10,
    val text: String,
    @SerialName("search_field") val searchField: String? = null,
    val experience: String? = null,
    val employment: String? = null,
    val schedule: String? = null,
    val area: String? = null,
    val metro: String? = null,
    @SerialName("professional_roles") val professionalRoles: String? = null,
    val industry: String? = null,
    @SerialName("employer_id") val employerId: String? = null,
    val currency: String? = null,
    val salary: Int? = null,
    val label: String? = null,
    @SerialName("only_with_salary") val onlyWithSalary: Boolean? = null,
    val period: Int? = null, // Количество дней, в пределах которых производится поиск по вакансиям
    @SerialName("date_from") val dateFrom: String? = null,
    @SerialName("date_to") val dateTo: String? = null,
    @SerialName("top_lat") val topLat: Double? = null,
    @SerialName("bottom_lat") val bottomLat: Double? = null,
    @SerialName("left_lng") val leftLng: Double? = null,
    @SerialName("right_lng") val rightLng: Double? = null,
    @SerialName("order_by") val orderBy: String? = null,
    @SerialName("sort_point_lng") val sortPointLng: Double? = null,
    @SerialName("sort_point_lat") val sortPointLat: Double? = null,
    val clusters: Boolean? = null,
    @SerialName("describe_arguments") val describeArguments: Boolean? = null,
    @SerialName("no_magic") val noMagic: Boolean? = null,
    val premium: Boolean? = null,
    @SerialName("responses_count_enabled") val responsesCountEnabled: Boolean? = null,
    @SerialName("part_time") val partTime: String? = null,
    @SerialName("accept_temporary") val acceptTemporary: Boolean? = null,
    @SerialName("locale") val locale: String = "RU",
    @SerialName("host") val host: String = "hh.ru"
) {
    companion object {
        private val json = Json { ignoreUnknownKeys = true }
        private val logger = LoggerFactory.getLogger(VacanciesRequest::class.java.canonicalName)
        private val savedRequestsDir = "saved_requests"

        /**
         * Reads the list of saved requests from file "saved_vacancy_requests.json"
         * and deserializes them into a list of [VacanciesRequest] objects.
         *
         * If the file does not exist, an empty list is returned.
         */
        fun readSavedRequests(): List<VacanciesRequest> {
            return try {
                val json = File("saved_vacancy_requests.json").readText()
                logger.info("Read saved_vacancy_requests.json")
                this.json.decodeFromString(json)
            } catch (e: java.io.FileNotFoundException) {
                emptyList()
            }
        }

        fun saveRequests(request: VacanciesRequest, fileName: String = "request.json") {
            val json = this.json.encodeToString(request)
            val file = File(savedRequestsDir, fileName)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            file.writeText(json)
            logger.info("Saved $file")
        }
    }
}

fun main() {
    val requests = VacanciesRequest.readSavedRequests()
    println(requests)
}