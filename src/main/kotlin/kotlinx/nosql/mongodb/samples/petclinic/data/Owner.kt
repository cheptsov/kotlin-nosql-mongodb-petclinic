package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.*
import org.joda.time.DateTime

object Owners : PersonSchema<Owner, Owners>("owners", javaClass()) {
    val address = string("address")
    val city = string("city")
    val telephone = string("telephone");

    {
        ensureIndex(text = array(firstName, lastName, telephone))
    }
}

class Owner(firstName: String, lastName: String, val address: String, val city: String, val telephone: String) : Person(firstName, lastName) {
    val id: Id<String, Owners>? = null
}