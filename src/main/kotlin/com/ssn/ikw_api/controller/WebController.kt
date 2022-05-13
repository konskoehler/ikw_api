package com.ssn.ikw_api.controller

import com.ssn.ikw_api.model.*
import com.ssn.ikw_api.service.CalculationService
import com.ssn.ikw_api.service.PersistenceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController(
    private val calculationService: CalculationService,
    private val persistenceService: PersistenceService,
) {

    @PutMapping("/product")
    fun saveProduct(@RequestBody productDtos: List<CreateProductRequest>): String {

        val products = productDtos.map { it.toDomain(persistenceService) }
        persistenceService.saveProducts(products)
        return "Done"
    }

    @RequestMapping("/product/all")
    fun findAllProducts(): List<Product> = persistenceService.listProducts()

    @RequestMapping("/product/calcRating/{externalId}")
    fun calcRating(@PathVariable externalId: Long): Product {
        val product = persistenceService.getProductByExternalId(externalId)!!
        return calculationService.calcRating(product)!!
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/ingredient")
    fun saveIngredients(
        @RequestBody ingredientDtos: List<CreateIngredientRequest>,
    ): String {
        val ingredients = ingredientDtos.map { it.toDomain() }.onEach {
            it.rating = calculationService.calcIngredientRating(it)
        }
        persistenceService.saveIngredients(ingredients)
        return "Done"
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/packaging")
    fun savePackaging(
        @RequestBody packagingDtos: List<CreatePackagingRequest>,
    ): String {
        val packagingList = packagingDtos.map { it.toDomain() }
        persistenceService.savePackaging(packagingList)
        return "Done"
    }

    @RequestMapping("/packaging/save")
    fun savePackaging(): String {
        persistenceService.savePackaging()
        return "Done"
    }
}