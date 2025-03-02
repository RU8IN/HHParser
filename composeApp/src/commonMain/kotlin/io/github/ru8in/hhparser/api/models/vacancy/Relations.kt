package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Relations(val code: String) {
    @SerialName("favourite") FAVOURITE("favourite"),
    @SerialName("got_response") GOT_RESPONSE("got_response"),
    @SerialName("got_invitation") GOT_INVITATION("got_invitation"),
    @SerialName("got_rejection") GOT_REJECTION("got_rejection"),
    @SerialName("blacklisted") BLACKLISTED("blacklisted");
}
