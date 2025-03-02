package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesResponse (
    val items: List<Vacancy>,
    val found: Int,
    val page: Int,
    val pages: Int,
    @SerialName("per_page") val perPage: Int,
)


@Serializable
data class VacanciesResponseError(
    val errors: List<Error>,
    val description: String? = null
) {
    @Serializable
    data class Error (
        val reason: String? = null,
        val type: String? = null,
        val value: String? = null,
        val code: Int, val message: String,
        @SerialName("captcha_url") val captchaUrl: String? = null,
        @SerialName("fallback_url") val fallbackUrl: String? = null
        )
}

class VacanciesErrorException(val errorInfo: VacanciesResponseError): Exception()