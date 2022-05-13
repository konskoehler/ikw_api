package com.ssn.ikw_api.service

import com.ssn.ikw_api.model.Ingredient
import com.ssn.ikw_api.model.Product
import org.springframework.stereotype.Service

@Service
class CalculationService {

    fun calcRating(product: Product): Product? {

        val miscibles = product.components.map { it.miscible }
        miscibles.onEach {
            if (it is Product) {
                it.rating = calcRating(it)!!.rating
            }
        }

        val ingredientRating = product.components.map { it.miscible.rating!! * it.relAmount }.sum()
        val energyRating = product.energyConsumption //todo turn into actual "rating"
        val packagingRating = product.packaging.rating

        val newRating = ingredientRating * 0.77 + energyRating * 0.11 + packagingRating * 0.14

        return product.apply { this.rating = newRating.toFloat() }
    }

    fun calcIngredientRating(ingredient: Ingredient): Float {
        val distanceRating = when (ingredient.distance) {
            "0-100" -> 10F
            "100-500" -> 7.5F
            "500-1000" -> 5F
            "1000-2000" -> 2.5F
            ">2000" -> 0F
            else -> throw IllegalArgumentException("Distance value is set incorrectly")
        }
        val trustRating = when (ingredient.trust) {
            "Sehr gut" -> 10F
            "Gut" -> 7.5F
            "Befriediegend" -> 5F
            "Mangelhaft" -> 2.5F
            "Schlecht" -> 0F
            else -> throw IllegalArgumentException("Trust value is set incorrectly")
        }
        val typeOfBusinessRating = when (ingredient.typeOfBusiness) {
            "1-200" -> 10F
            "200-500" -> 7.5F
            "500-1000" -> 5F
            "1000-2000" -> 2.5F
            ">2000" -> 0F
            else -> throw IllegalArgumentException("TypeOfBusiness value is set incorrectly")
        }
        val certificateRating = when (ingredient.certificate) {
            "Öko-effektiv (Bio)" -> 10F
            "Öko-effizient / Fair trade" -> 7.5F
            "Konvetionell" -> 2.5F
            "Risikobehaftet" -> 2.5F
            "Problembehaftet" -> 0F
            else -> throw IllegalArgumentException("Certificate value is set incorrectly")
        }

        return (distanceRating * 0.10 + trustRating * 0.1 + typeOfBusinessRating * 0.1 + certificateRating * 0.7).toFloat()
    }
}