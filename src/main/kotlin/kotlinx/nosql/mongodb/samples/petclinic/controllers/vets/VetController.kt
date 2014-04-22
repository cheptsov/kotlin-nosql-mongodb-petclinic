package kotlinx.nosql.mongodb.samples.petclinic.controllers.owners

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import org.springframework.ui.Model
import kotlinx.nosql.mongodb.samples.petclinic.data.Owners
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMethod
import kotlinx.nosql.mongodb.samples.petclinic.data.Owner
import kotlinx.nosql.mongodb.samples.petclinic.data.Pets
import org.springframework.beans.factory.annotation.Autowired
import kotlinx.nosql.mongodb.samples.petclinic.data.PetTypes
import kotlinx.nosql.mongodb.samples.petclinic.data.Pet
import kotlinx.nosql.mongodb.samples.petclinic.data.PetType
import java.util.regex.Pattern
import kotlinx.nosql.mongodb.samples.petclinic.data.Vets

Controller RequestMapping(value = array("/vets"))
class VetController [Autowired] (val db: MongoDB) {
    RequestMapping(array("/"), method = array(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("vets", Vets.findAll().toList())
        }
        return "/vets/index"
    }
}