package com.ssn.ikw_api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.ssn.ikw_api.service.PersistenceService

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateProductRequest(
    @JsonProperty("external_id")
    val externalId: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("energy_consumption")
    val energyConsumption: Float,
    @JsonProperty("components")
    val components: List<CreateComponentRequest>,
    @JsonProperty("packaging")
    val packagingName: String,
)

fun CreateProductRequest.toDomain(persistenceService: PersistenceService) = Product(
    externalId = externalId,
    name = name,
    energyConsumption = energyConsumption,
    components = components.map { it.toDomain(persistenceService) },
    packaging = persistenceService.getPackaging(packagingName)!!
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateComponentRequest(
    @JsonProperty("miscible_external_id")
    val miscibleExternalId: Long,
    @JsonProperty("rel_amount")
    val relAmount: Float,
)

fun CreateComponentRequest.toDomain(persistenceService: PersistenceService) =
    Component(
        miscible = this.let {
            val product = persistenceService.getProductByExternalId(miscibleExternalId)
            if (product != null) {
                return@let product
            } else {
                return@let persistenceService.getIngredient(miscibleExternalId) as Miscible
            }
        },
        relAmount = relAmount,
    )