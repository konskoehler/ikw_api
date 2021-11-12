package com.ssn.ikw_api.model

data class Response(
        val rating: Float,
        val ingredientsRating: Float,
        val energyConsumptionRating: Float,
        val packagingRating: Float,
        val product: Product
)