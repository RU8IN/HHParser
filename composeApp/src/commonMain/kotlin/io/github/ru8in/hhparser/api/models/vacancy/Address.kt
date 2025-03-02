package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val building: String? = null,
    val city: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val street: String? = null,
    val description: String? = null,
    @SerialName("metro_stations") val metroStations: List<MetroStation>? = null
) {
    @Serializable
    data class MetroStation(
        val lat: Double? = null,
        @SerialName("line_id") val lineId: String,
        @SerialName("line_name") val lineName: String,
        val lng: Double? = null,
        @SerialName("station_id") val stationId: String,
        @SerialName("station_name") val stationName: String
    )
}
