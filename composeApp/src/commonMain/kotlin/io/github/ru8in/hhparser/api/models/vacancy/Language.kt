package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val id: String,
    val level: LanguageLevel,
    val name: String
) {
    @Serializable
    data class LanguageLevel(
        val id: String,
        val name: String
    )
}
