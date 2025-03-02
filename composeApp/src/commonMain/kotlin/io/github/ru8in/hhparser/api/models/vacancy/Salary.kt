package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    val currency: String? = null, // Код валюты из справочника currency
    val from: Int? = null, // Нижняя граница зарплаты
    val gross: Boolean? = null, // Признак что границы зарплаты указаны до вычета налогов
    val to: Int? = null // Верхняя граница зарплаты
)