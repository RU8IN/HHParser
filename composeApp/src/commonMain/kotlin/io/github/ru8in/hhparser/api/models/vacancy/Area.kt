package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val id: String,
    val name: String,
    val url: String
)
