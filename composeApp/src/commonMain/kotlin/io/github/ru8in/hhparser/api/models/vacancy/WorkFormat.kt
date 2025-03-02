package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class WorkFormat (
    val id: String,
    val name: String
)
