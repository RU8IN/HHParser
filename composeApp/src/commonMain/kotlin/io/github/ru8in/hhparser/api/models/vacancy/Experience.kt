package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Experience(
    val id: String,
    val name: String
)
