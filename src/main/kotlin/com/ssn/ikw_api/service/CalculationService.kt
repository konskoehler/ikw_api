package com.ssn.ikw_api.service

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

        product.rating = mutableListOf<Float>().also { list ->
            list.add(product.components.map { it.miscible.rating!! * it.relAmount }.sum())
            list.add(product.energyConsumption.rating.toFloat())
            list.add(product.packaging.rating.toFloat())
        }.average().toFloat()

        return product
    }
}