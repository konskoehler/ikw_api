package com.ssn.ikw_api.service

import com.ssn.ikw_api.model.Component
import com.ssn.ikw_api.model.EnergyConsumption
import com.ssn.ikw_api.model.Ingredient
import com.ssn.ikw_api.model.Packaging
import com.ssn.ikw_api.model.Product
import com.ssn.ikw_api.repository.ComponentRepository
import com.ssn.ikw_api.repository.IngredientRepository
import com.ssn.ikw_api.repository.PackagingRepository
import com.ssn.ikw_api.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class PersistenceService(
    private val componentRepository: ComponentRepository,
    private val ingredientRepository: IngredientRepository,
    private val packagingRepository: PackagingRepository,
    private val productRepository: ProductRepository,
) {
    fun saveProduct() {
        productRepository.save(
            Product(
                name = "Hackfleisch",
                energyConsumption = EnergyConsumption(kwhPerKg = 1F, lPerKg = 100F, rating = 5),
                packaging = packagingRepository.findByName("Einfachplastik")!!,
                components = listOf(
                    Component(miscible = ingredientRepository.findByName("Rindfleisch")!!, relAmount = 0.7F),
                    Component(miscible = ingredientRepository.findByName("Salz")!!, relAmount = 0.2F),
                    Component(miscible = productRepository.findByName("Kräuterbutter")!!, relAmount = 0.1F)
                )

            )
        )
    }

    fun listProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    fun getProduct(id: Long): Product {
        val res = productRepository.findById(id)
        return if (res.isPresent) {
            res.get()
        } else {
            throw IllegalArgumentException("Product with id=$id not found.")
        }
    }

    fun saveIngredients() {
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
            )
        )

        // TODO verschieben
        productRepository.save(
            Product(
                name = "Kräuterbutter",
                energyConsumption = EnergyConsumption(kwhPerKg = 12F, lPerKg = 100F, rating = 12),
                packaging = packagingRepository.findByName("Einfachplastik")!!,
                components = listOf(
                    Component(miscible = ingredientRepository.findByName("Butter")!!, relAmount = 0.5F),
                    Component(miscible = ingredientRepository.findByName("Kräuter")!!, relAmount = 0.5F)
                )
            )
        )
    }

    fun savePackaging() {
        packagingRepository.save(
            Packaging(
                name = "Einfachplastik",
                rating = 2
            )
        )
    }
}