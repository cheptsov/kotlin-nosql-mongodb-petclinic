package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.DocumentSchema

object Vets : DocumentSchema<Vet>("vets", Vet::class) {
    val firstName = string("firstName")
    val lastName = string("lastName")
    val specialities = setOfString("specialities")
}

class Vet(val firstName: String, val lastName : String, val specialities: Set<String>) {
    val id : Id<String, Vets>? = null
}