package com.ssn.ikw_api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreatePackagingRequest(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("rating")
    val rating: Float,
)

fun CreatePackagingRequest.toDomain() = Packaging(
    name = name,
    rating = rating
)
