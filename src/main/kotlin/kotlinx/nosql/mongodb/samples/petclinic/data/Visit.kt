package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*

object Visits : Schema<Visit>("petTypes", javaClass()) {
    val visitDate = date("visitDate")
    val description = string("description")
    val petId = id("petId", Pets)
}

class Visit() {
    val id : Id<String, Visits>? = null
}