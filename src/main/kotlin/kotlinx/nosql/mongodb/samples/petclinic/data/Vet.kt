package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*

object Vets : PersonSchema<Vet, Vets>("pets", javaClass()) {
    val specialities = setOfString("specialities")
}

class Vet(firstName: String, lastName : String) : Person(firstName, lastName) {
    val id : Id<String, Owners>? = null
}