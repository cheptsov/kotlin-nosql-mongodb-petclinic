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
import kotlinx.nosql.mongodb.samples.petclinic.data.Specialities
import kotlinx.nosql.mongodb.samples.petclinic.data.Speciality

Controller RequestMapping(value = array("/specialities"))
class SpecialityController [Autowired] (val db: MongoDB) {
    RequestMapping(array("/add"), method = array(RequestMethod.GET))
    fun add(): String {
        return "/specialities/add"
    }

    RequestMapping(value = array("/add"), method = array(RequestMethod.POST))
    public fun add(RequestParam("name") nameParam: String): String {
        db.withSession {
            Specialities.insert(Speciality(nameParam))
        }
        return "redirect:/specialities/";
    }

    RequestMapping(value = array("/edit"), method = array(RequestMethod.POST))
    public fun edit(RequestParam("id") idParam: String,
                    RequestParam("name") nameParam: String): String {
        db.withSession {
            Specialities.select { name }.find(Id(idParam)).set(nameParam)
        }
        return "redirect:/specialities/";
    }

    RequestMapping(array("/edit"), method = array(RequestMethod.GET))
    fun edit(RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val speciality = Specialities.get(Id(idParam))
            model.addAttribute("id", speciality.id!!.value)
            model.addAttribute("name", speciality.name)
        }
        return "/specialities/edit"
    }

    RequestMapping(array("/delete"), method = array(RequestMethod.POST))
    fun delete(RequestParam("id") idParam: String): String {
        db.withSession {
            Specialities.delete { id.equal(Id(idParam)) }
        }
        return "redirect:/specialities/";
    }

    RequestMapping(array("/"), method = array(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("specialities", Specialities.findAll().toList())
        }
        return "/specialities/index"
    }
}