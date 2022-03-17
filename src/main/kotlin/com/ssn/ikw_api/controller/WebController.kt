package com.ssn.ikw_api.controller

import com.ssn.ikw_api.model.*
import com.ssn.ikw_api.repository.IngredientRepository
import com.ssn.ikw_api.repository.PackagingRepository
import com.ssn.ikw_api.repository.ProductRepository
import com.ssn.ikw_api.service.CalculationService
import com.ssn.ikw_api.service.PersistenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController(
    private val calculationService: CalculationService,
    private val persistenceService: PersistenceService,
) {


    @RequestMapping("/product/save")
    fun saveProduct(): String {
        persistenceService.saveProduct()
        return "Done"
    }

    @RequestMapping("/product/all")
    fun findAllProducts(): List<Product> = persistenceService.listProducts()

    @RequestMapping("/product/calcRating/{id}")
    fun calcRating(@PathVariable id: Long): Product {
        val product = persistenceService.getProduct(id)
        return calculationService.calcRating(product)!!
    }

    @RequestMapping("/ingredient/save")
    fun saveIngredients(): String {

        persistenceService.saveIngredients()
        return "Done"
    }


    @RequestMapping("/packaging/save")
    fun savePackaging(): String {
        persistenceService.savePackaging()
        return "Done"
    }

    @RequestMapping("/prepare")
    fun prepare(): String {
        savePackaging()
        saveIngredients()
        saveProduct()
        return "Done"
    }
}