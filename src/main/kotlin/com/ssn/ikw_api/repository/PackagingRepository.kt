package com.ssn.ikw_api.repository

import com.ssn.ikw_api.model.Packaging
import org.springframework.data.repository.CrudRepository

interface PackagingRepository : CrudRepository<Packaging, Long> {
    fun findByName(name: String): Packaging?
}