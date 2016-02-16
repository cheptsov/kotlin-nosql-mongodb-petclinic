package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.DocumentSchema
import org.joda.time.DateTime

object Specialities : DocumentSchema<Speciality>("specialities", Speciality::class) {
    val name = string("name");

    init {
        ensureIndex(unique = true, ascending = arrayOf(name))
    }
}

data class Speciality(val name: String) {
    val id : Id<String, Specialities>? = null
}