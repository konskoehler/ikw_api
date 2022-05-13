package com.ssn.ikw_api.service

import com.ssn.ikw_api.model.Ingredient
import com.ssn.ikw_api.model.Packaging
import com.ssn.ikw_api.model.Product
import com.ssn.ikw_api.repository.IngredientRepository
import com.ssn.ikw_api.repository.PackagingRepository
import com.ssn.ikw_api.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class PersistenceService(
    private val ingredientRepository: IngredientRepository,
    private val packagingRepository: PackagingRepository,
    private val productRepository: ProductRepository,
) {

    fun saveProducts(products: List<Product>) {
        productRepository.saveAll(products)
    }

    fun getProductByExternalId(externalId: Long): Product? {
        return productRepository.findByExternalId(externalId)
    }

    fun listProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    fun saveIngredients(ingredients: List<Ingredient>) {
        ingredientRepository.saveAll(ingredients)
    }

    fun getIngredient(externalId: Long): Ingredient? {
        return ingredientRepository.findByExternalId(externalId)
    }

    fun savePackaging(packagingList: List<Packaging>) {
        packagingRepository.saveAll(packagingList)
    }

    fun getPackaging(name: String): Packaging? {
        return packagingRepository.findByName(name)
    }
}