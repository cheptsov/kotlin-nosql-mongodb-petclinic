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

@Controller @RequestMapping(value = "/petTypes")
class PetTypeController @Autowired constructor (val db: MongoDB) {
    @RequestMapping("/add", method = arrayOf(RequestMethod.GET))
    fun add(): String {
        return "petTypes/add"
    }

    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST))
    public fun add(@RequestParam("name") nameParam: String): String {
        db.withSession {
            PetTypes.insert(PetType(nameParam))
        }
        return "redirect:/petTypes/";
    }

    @RequestMapping(value = "/edit", method = arrayOf(RequestMethod.POST))
    public fun edit(@RequestParam("id") idParam: String,
                    @RequestParam("name") nameParam: String): String {
        db.withSession {
            PetTypes.find { id.equal(Id(idParam)) }.projection { name }.update(nameParam)
        }
        return "redirect:/petTypes/";
    }

    @RequestMapping("/edit", method = arrayOf(RequestMethod.GET))
    fun edit(@RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val petType = PetTypes.find { id.equal(Id(idParam)) }.single()
            model.addAttribute("id", petType.id!!.value)
            model.addAttribute("name", petType.name)
        }
        return "petTypes/edit"
    }

    @RequestMapping("/delete", method = arrayOf(RequestMethod.POST))
    fun delete(@RequestParam("id") idParam: String): String {
        db.withSession {
            PetTypes.find { id.equal(Id(idParam)) }.remove()
        }
        return "redirect:/petTypes/";
    }

    @RequestMapping("/", method = arrayOf(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("petTypes", PetTypes.find().toList())
        }
        return "petTypes/index"
    }
}