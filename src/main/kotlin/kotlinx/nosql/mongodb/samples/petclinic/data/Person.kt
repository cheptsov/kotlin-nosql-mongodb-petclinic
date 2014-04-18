package kotlinx.nosql.mongodb.samples.petclinic.data

import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*

abstract class PersonSchema<D : Person, S : Schema<D>>(name: String, valueClass: Class<D>) : Schema<D>(name, valueClass) {
    val firstName = string<S>("firstName")
    val lastName = string<S>("lastName")
}

abstract class Person(val firstName: String, val lastName : String)
