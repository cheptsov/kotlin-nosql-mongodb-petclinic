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

@Controller @RequestMapping(value = "/specialities")
class SpecialityController @Autowired constructor (val db: MongoDB) {
    @RequestMapping("/add", method = arrayOf(RequestMethod.GET))
    fun add(): String {
        return "specialities/add"
    }

    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST))
    public fun add(@RequestParam("name") nameParam: String): String {
        db.withSession {
            Specialities.insert(Speciality(nameParam))
        }
        return "redirect:/specialities/";
    }

    @RequestMapping(value = "/edit", method = arrayOf(RequestMethod.POST))
    public fun edit(@RequestParam("id") idParam: String,
                    @RequestParam("name") nameParam: String): String {
        db.withSession {
            Specialities.find{ id.equal(Id(idParam)) }.projection { name }.update(nameParam)
        }
        return "redirect:/specialities/";
    }

    @RequestMapping("/edit", method = arrayOf(RequestMethod.GET))
    fun edit(@RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val speciality = Specialities.find { id.equal(Id(idParam)) }.single()
            model.addAttribute("id", speciality.id!!.value)
            model.addAttribute("name", speciality.name)
        }
        return "specialities/edit"
    }

    @RequestMapping("/delete", method = arrayOf(RequestMethod.POST))
    fun delete(@RequestParam("id") idParam: String): String {
        db.withSession {
            Specialities.find { id.equal(Id(idParam)) }.remove()
        }
        return "redirect:/specialities/";
    }

    @RequestMapping("/", method = arrayOf(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("specialities", Specialities.find().toList())
        }
        return "specialities/index"
    }
}