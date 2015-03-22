package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.DocumentSchema
import org.joda.time.DateTime

object Specialities : DocumentSchema<Speciality>("specialities", javaClass()) {
    val name = string("name");

    {
        ensureIndex(unique = true, ascending = array(name))
    }
}

class Speciality(val name: String) {
    val id : Id<String, Specialities>? = null
}