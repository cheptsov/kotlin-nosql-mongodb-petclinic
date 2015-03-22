package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.*
import org.joda.time.DateTime
import kotlinx.nosql.mongodb.DocumentSchema

object Owners : DocumentSchema<Owner>("owners", javaClass()) {
    val firstName = string("firstName")
    val lastName = string("lastName")
    val address = string("address")
    val city = string("city")
    val telephone = string("telephone")

    init {
        ensureIndex(text = array(firstName, lastName, telephone))
    }
}

class Owner(val firstName: String, val lastName: String, val address: String, val city: String, val telephone: String) {
    val id: Id<String, Owners>? = null
}
