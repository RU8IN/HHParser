package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employer(
    @SerialName("accredited_it_employer") val accreditedItEmployer: Boolean,
    @SerialName("alternate_url") val alternateUrl: String? = null,
    @SerialName("employer_rating") val employerRating: EmployerRating? = null,
    val id: String? = null,
    @SerialName("logo_urls") val logoUrls: LogoUrls? = null,
    val name: String,
    val trusted: Boolean,
    val url: String? = null,
    @SerialName("vacancies_url") val vacanciesUrl: String? = null,
    val blacklisted: Boolean? = null,
    @SerialName("applicant_services") val applicantServices: ApplicantService? = null
) {
    @Serializable
    data class EmployerRating(
        @SerialName("reviews_count") val reviewsCount: Int? = null,
        @SerialName("total_rating") val totalRating: String? = null
    )

    @Serializable
    data class LogoUrls(
        @SerialName("90") val _90: String? = null,
        @SerialName("240") val _240: String? = null,
        @SerialName("original") val original: String? = null
    )

    @Serializable
    data class ApplicantService (
        @SerialName("target_employer") val targetEmployer: TargetEmployer
    ) {
        @Serializable
        data class TargetEmployer(
            val count: Int
        )
    }

}
