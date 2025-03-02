package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contacts(
    @SerialName("call_tracking_enabled") val callTrackingEnabled: Boolean? = null,
    val email: String? = null,
    val name: String? = null,
    val phones: List<Phone>? = null,
) {
    @Serializable
    data class Phone(
        val city: String,
        val comment: String? = null,
        val country: String,
        val formatted: String,
        val number: String
    )
}
