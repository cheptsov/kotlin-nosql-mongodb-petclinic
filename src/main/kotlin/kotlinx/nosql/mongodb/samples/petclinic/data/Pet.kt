package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.DocumentSchema
import org.joda.time.LocalDate

object Pets : DocumentSchema<Pet>("pets", Pet::class) {
    val name = string("name")
    val birthDate = date("birthDate")
    val typeId = id("typeId", PetTypes)
    val ownerId = id("ownerId", Owners)
}

class Pet(val name: String, val birthDate: LocalDate, val typeId: Id<String, PetTypes>, val ownerId: Id<String, Owners>) {
    val id : Id<String, Owners>? = null
}