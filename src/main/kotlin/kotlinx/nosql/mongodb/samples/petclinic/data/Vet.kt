package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.*
import kotlinx.nosql.mongodb.*

object Vets : DocumentSchema<Vet>("vets", javaClass()) {
    val firstName = string("firstName")
    val lastName = string("lastName")
    val specialities = setOfString("specialities")
}

class Vet(val firstName: String, val lastName : String, val specialities: Set<String>) {
    val id : Id<String, Vets>? = null
}
