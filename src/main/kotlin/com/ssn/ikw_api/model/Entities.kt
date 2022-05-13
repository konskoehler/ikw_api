package com.ssn.ikw_api.model

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Miscible(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        open val id: Long = -1,
        open val externalId: Long,
        open var rating: Float? = null
)

@Entity
class Product(
        externalId: Long,
        val name: String,
        // @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = EnergyConsumption::class)
        val energyConsumption: Float,
        @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, targetEntity = Component::class)
        val components: List<Component>,
        @ManyToOne
        val packaging: Packaging
) : Miscible(externalId = externalId)

// @Entity
// data class EnergyConsumption(
//         @Id
//         @GeneratedValue(strategy = GenerationType.AUTO)
//         val id: Long = -1,
//         val kwhPerKg: Float,
//         val lPerKg: Float,
//         val rating: Int
// )

@Entity
class Component(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        @ManyToOne
        val miscible: Miscible,
 /*       @ColumnTransformer(
                read =  "pgp_sym_decrypt(" +
                        "    rel_amount::bytea, " +
                        "    current_setting('key.recipe')" +
                        ")",
                write = "pgp_sym_encrypt( " +
                        "    ?::text, " +
                        "    current_setting('key.recipe')" +
                        ") "
        )
        @Column(columnDefinition = "bytea")*/
        val relAmount: Float
)

@Entity
class Ingredient(
        externalId: Long,
        val name: String,
        val productClass: String,
        val distance: String,
        val certificate: String,
        val typeOfBusiness: String,
        val trust: String,
        rating: Float? = null,
) : Miscible(externalId = externalId, rating = rating)

@Entity
class Packaging(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        @Column(unique = true)
        val name: String,
        val rating: Float
)