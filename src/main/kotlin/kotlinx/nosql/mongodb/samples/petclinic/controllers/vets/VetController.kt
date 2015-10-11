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
import kotlinx.nosql.mongodb.samples.petclinic.data.Specialities
import org.springframework.web.context.request.WebRequest
import java.util.HashSet
import kotlinx.nosql.mongodb.samples.petclinic.data.Vet

@Controller @RequestMapping(value = "/vets")
class VetController @Autowired constructor (val db: MongoDB) {
    @RequestMapping("/", method = arrayOf(RequestMethod.GET))
    fun index(model: Model): String {
        db.withSession {
            model.addAttribute("vets", Vets.find().toList())
        }
        return "vets/index"
    }

    @RequestMapping("/add", method = arrayOf(RequestMethod.GET))
    fun add(model: Model): String {
        db.withSession {
            model.addAttribute("specialities", Specialities.find().toList())
        }
        return "vets/add"
    }

    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST))
    public fun add(@RequestParam("firstName") firstNameParam: String,
                   @RequestParam("lastName") lastNameParam: String, webRequest: WebRequest): String {
        db.withSession {
            val specialities = HashSet<String>()
            Specialities.find().forEach {
                if (webRequest.getParameter(it.name) != null) {
                    specialities.add(it.name)
                }
            }
            Vets.insert(Vet(firstNameParam, lastNameParam, specialities))
        }
        return "redirect:/vets/";
    }

    @RequestMapping("/edit", method = arrayOf(RequestMethod.GET))
    fun edit(@RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val vet = Vets.find { id.equal(Id(idParam)) }.single()
            model.addAttribute("vet", vet)
            model.addAttribute("specialities", Specialities.find().toList())
        }
        return "vets/edit"
    }

    @RequestMapping(value = "/edit", method = arrayOf(RequestMethod.POST))
    public fun edit(@RequestParam("id") idParam: String,
                    @RequestParam("firstName") firstNameParam: String,
                   @RequestParam("lastName") lastNameParam: String, webRequest: WebRequest): String {
        db.withSession {
            val newSpecialities = HashSet<String>()
            Specialities.find().forEach {
                if (webRequest.getParameter(it.name) != null) {
                    newSpecialities.add(it.name)
                }
            }
            Vets.find { id.equal(Id(idParam)) }.projection { firstName + lastName + specialities }.update(firstNameParam, lastNameParam, newSpecialities)
        }
        return "redirect:/vets/";
    }
}