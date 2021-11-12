package com.ssn.ikw_api.repository

import com.ssn.ikw_api.model.Ingredient
import com.ssn.ikw_api.model.Packaging
import org.springframework.data.repository.CrudRepository

interface IngredientRepository : CrudRepository<Ingredient, Long> {
    fun findByName(name: String): Ingredient?
}