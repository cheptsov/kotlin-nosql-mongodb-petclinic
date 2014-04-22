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

Controller RequestMapping(value = array("/petTypes"))
class PetTypeController [Autowired] (val db: MongoDB) {
    RequestMapping(array("/add"), method = array(RequestMethod.GET))
    fun add(): String {
        return "petTypes/add"
    }

    RequestMapping(value = array("/add"), method = array(RequestMethod.POST))
    public fun add(RequestParam("name") nameParam: String): String {
        db.withSession {
            PetTypes.insert(PetType(nameParam))
        }
        return "redirect:/petTypes/";
    }

    RequestMapping(value = array("/edit"), method = array(RequestMethod.POST))
    public fun edit(RequestParam("id") idParam: String,
                    RequestParam("name") nameParam: String): String {
        db.withSession {
            PetTypes.select { name }.find(Id(idParam)).set(nameParam)
        }
        return "redirect:/petTypes/";
    }

    RequestMapping(array("/edit"), method = array(RequestMethod.GET))
    fun edit(RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val petType = PetTypes.get(Id(idParam))
            model.addAttribute("id", petType.id!!.value)
            model.addAttribute("name", petType.name)
        }
        return "petTypes/edit"
    }

    RequestMapping(array("/delete"), method = array(RequestMethod.POST))
    fun delete(RequestParam("id") idParam: String): String {
        db.withSession {
            PetTypes.delete { id.equal(Id(idParam)) }
        }
        return "redirect:/petTypes/";
    }

    RequestMapping(array("/"), method = array(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("petTypes", PetTypes.findAll().toList())
        }
        return "petTypes/index"
    }
}