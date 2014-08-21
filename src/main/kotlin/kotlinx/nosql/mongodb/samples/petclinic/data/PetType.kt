package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.*
import kotlinx.nosql.mongodb.*
import org.joda.time.DateTime

object PetTypes : DocumentSchema<PetType>("petTypes", javaClass()) {
    val name = string("name")
}

class PetType(val name: String) {
    val id : Id<String, PetTypes>? = null
}
