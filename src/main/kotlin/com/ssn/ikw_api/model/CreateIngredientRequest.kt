package com.ssn.ikw_api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateIngredientRequest(
    @JsonProperty("external_id")
    val externalId: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("product_class")
    val productClass: String,
    @JsonProperty("distance")
    val distance: String,
    @JsonProperty("certificate")
    val certificate: String,
    @JsonProperty("type_of_business")
    val typeOfBusiness: String,
    @JsonProperty("trust")
    val trust: String,
)

fun CreateIngredientRequest.toDomain() = Ingredient(
    externalId = externalId,
    name = name,
    productClass = productClass,
    distance = distance,
    certificate = certificate,
    typeOfBusiness = typeOfBusiness,
    trust = trust,
)
