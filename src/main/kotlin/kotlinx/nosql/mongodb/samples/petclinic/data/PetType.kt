package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import org.joda.time.DateTime

object PetTypes : Schema<PetType>("petTypes", javaClass()) {
    val name = string("name")
}

class PetType(val name: String) {
    val id : Id<String, PetTypes>? = null
}