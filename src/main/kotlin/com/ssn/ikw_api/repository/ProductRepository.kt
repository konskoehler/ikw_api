package com.ssn.ikw_api.repository

import com.ssn.ikw_api.model.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
    fun findByExternalId(externalId: Long): Product?
}