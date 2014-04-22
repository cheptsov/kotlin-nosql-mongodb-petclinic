package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import org.joda.time.DateTime

object Specialities : Schema<Speciality>("specialities", javaClass()) {
    val name = string("name");

    {
        ensureIndex(unique = true, ascending = array(name))
    }
}

class Speciality(val name: String) {
    val id : Id<String, Specialities>? = null
}