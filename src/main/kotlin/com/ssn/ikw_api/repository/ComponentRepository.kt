package com.ssn.ikw_api.repository

import com.ssn.ikw_api.model.Component
import org.springframework.data.repository.CrudRepository

interface ComponentRepository : CrudRepository<Component, Long> {
}