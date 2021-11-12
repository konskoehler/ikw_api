package com.ssn.ikw_api.controller

import com.ssn.ikw_api.model.*
import com.ssn.ikw_api.repository.IngredientRepository
import com.ssn.ikw_api.repository.PackagingRepository
import com.ssn.ikw_api.repository.ProductRepository
import com.ssn.ikw_api.service.CalculationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var packagingRepository: PackagingRepository

    @Autowired
    lateinit var ingredientRepository: IngredientRepository

    @Autowired
    lateinit var calculationService: CalculationService

    @RequestMapping("/product/save")
    fun saveProduct(): String {
        productRepository.save(
                Product(name = "Hackfleisch",
                        energyConsumption = EnergyConsumption(kwhPerKg = 1F, lPerKg = 100F, rating = 5),
                        packaging = packagingRepository.findByName("Einfachplastik")!!,
                        components = listOf(
                                Component(miscible = ingredientRepository.findByName("Rindfleisch")!!, relAmount = 0.7F),
                                Component(miscible = ingredientRepository.findByName("Salz")!!, relAmount = 0.2F),
                                Component(miscible = productRepository.findByName("Kräuterbutter")!!, relAmount = 0.1F)
                        )

                )
        )
        return "Done"
    }

    @RequestMapping("/product/all")
    fun findAllProducts(): MutableIterable<Product> = productRepository.findAll()

    @RequestMapping("/product/calcRating/{id}")
    fun calcRating(@PathVariable id: Long): Product {

        val product = productRepository.findById(id)
        if (product.isPresent) {
            return calculationService.calcRating(product.get())!!
        } else {
            throw IllegalArgumentException("Product with id=$id not found.")
        }
    }

    @RequestMapping("/ingredient/save")
    fun saveIngredients(): String {
        ingredientRepository.saveAll(
                listOf(
                        Ingredient(
                                name = "Rindfleisch",
                                productClass = "Organischer Rohstoff",
                                distance = "0-100",
                                certificate = "Öko-effektiv (Bio)",
                                typeOfBusiness = "1-200",
                                trust = "Sehr gut",
                                ratingInteger = 7
                        ),
                        Ingredient(
                                name = "Salz",
                                productClass = "Organischer Rohstoff",
                                distance = "100-1000",
                                certificate = "Öko-effektiv (Bio)",
                                typeOfBusiness = "200-1000",
                                trust = "Gut",
                                ratingInteger = 4
                        ),
                        Ingredient(
                                name = "Butter",
                                productClass = "Organischer Rohstoff",
                                distance = "100-1000",
                                certificate = "Öko-effektiv (Bio)",
                                typeOfBusiness = "200-1000",
                                trust = "Gut",
                                ratingInteger = 6
                        ),
                        Ingredient(
                                name = "Kräuter",
                                productClass = "Organischer Rohstoff",
                                distance = "100-1000",
                                certificate = "Öko-effektiv (Bio)",
                                typeOfBusiness = "200-1000",
                                trust = "Gut",
                                ratingInteger = 10
                        )
                ))
        productRepository.save(Product(
                name = "Kräuterbutter",
                energyConsumption = EnergyConsumption(kwhPerKg = 12F, lPerKg = 100F, rating = 12),
                packaging = packagingRepository.findByName("Einfachplastik")!!,
                components = listOf(
                        Component(miscible = ingredientRepository.findByName("Butter")!!, relAmount = 0.5F),
                        Component(miscible = ingredientRepository.findByName("Kräuter")!!, relAmount = 0.5F)
                )))
        return "Done"
    }


    @RequestMapping("/packaging/save")
    fun savePackaging(): String {
        packagingRepository.save(
                Packaging(
                        name = "Einfachplastik",
                        rating = 2
                )
        )
        return "Done"
    }

    @RequestMapping("/prepare")
    fun prepare(): String {
        savePackaging()
        saveIngredients()
        saveProduct()
        return "Done"
    }


//    @RequestMapping("/product/findById/{id}")
//    fun findById(@PathVariable id: Long) = productRepository.findById(id)
//
//    @RequestMapping("/product/findByName/{name}")
//    fun findByLastName(@PathVariable name: String) = productRepository.findByName(name)
}