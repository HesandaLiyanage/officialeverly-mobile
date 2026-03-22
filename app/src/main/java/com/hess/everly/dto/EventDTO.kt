package com.hess.everly.dto

import com.google.gson.annotations.SerializedName

data class EventDTO(
    @SerializedName("eventId") val eventId: Int,
    @SerializedName("eventName") val eventName: String?,
    @SerializedName("eventDate") val eventDate: String?,
    @SerializedName("eventLocation") val eventLocation: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("coverImage") val coverImage: String?
)

data class EventsResponse(
    @SerializedName("upcoming") val upcoming: List<EventDTO>?,
    @SerializedName("past") val past: List<EventDTO>?
)
