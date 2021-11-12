package com.ssn.ikw_api.model

import org.hibernate.annotations.ColumnTransformer
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Miscible(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        val id: Long = -1,
        open var rating: Float? = null
)

@Entity
data class Product(
        val name: String,
        @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = EnergyConsumption::class)
        val energyConsumption: EnergyConsumption,
        @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, targetEntity = Component::class)
        val components: List<Component>,
        @ManyToOne
        val packaging: Packaging
) : Miscible()

@Entity
data class EnergyConsumption(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val kwhPerKg: Float,
        val lPerKg: Float,
        val rating: Int
)

@Entity
data class Component(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        @ManyToOne
        val miscible: Miscible,
        @ColumnTransformer(
                read =  "pgp_sym_decrypt(" +
                        "    rel_amount::bytea, " +
                        "    current_setting('key.recipe')" +
                        ")",
                write = "pgp_sym_encrypt( " +
                        "    ?::text, " +
                        "    current_setting('key.recipe')" +
                        ") "
        )
        @Column(columnDefinition = "bytea")
        val relAmount: Float
)

@Entity
data class Ingredient(
        val name: String,
        val productClass: String,
        val distance: String,
        val certificate: String,
        val typeOfBusiness: String,
        val trust: String,
        val ratingInteger: Int
) : Miscible(rating = ratingInteger.toFloat())

@Entity
data class Packaging(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        @Column(unique = true)
        val name: String,
        val rating: Int
)