package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.DocumentSchema
import org.joda.time.DateTime

object PetTypes : DocumentSchema<PetType>("petTypes", PetType::class) {
    val name = string("name")
}

class PetType(val name: String) {
    val id : Id<String, PetTypes>? = null
}