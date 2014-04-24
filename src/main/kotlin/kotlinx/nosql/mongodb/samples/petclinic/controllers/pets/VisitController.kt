package kotlinx.nosql.mongodb.samples.petclinic.controllers.owners

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import org.springframework.ui.Model
import kotlinx.nosql.mongodb.samples.petclinic.data.Owners
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMethod
import kotlinx.nosql.mongodb.samples.petclinic.data.Pets
import kotlinx.nosql.mongodb.samples.petclinic.data.PetTypes
import kotlinx.nosql.mongodb.samples.petclinic.data.Pet
import org.joda.time.LocalDate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.beans.factory.annotation.Autowired
import kotlinx.nosql.mongodb.samples.petclinic.data.Visits
import kotlinx.nosql.mongodb.samples.petclinic.data.Visit

Controller RequestMapping(value = array("/visits"))
class VisitController [Autowired] (val db: MongoDB) {
    RequestMapping(value = array("/add"), method = array(RequestMethod.GET))
    public fun add(RequestParam("petId") petIdParam: String, model: Model): String {
        db.withSession {
            val pet = Pets.get(Id(petIdParam))
            model.addAttribute("pet", pet)
            val owner = Owners.get(pet.ownerId)
            model.addAttribute("owner", owner)
        }
        return "visits/add"
    }

    RequestMapping(value = array("/add"), method = array(RequestMethod.POST))
    public fun add(RequestParam("petId") petIdParam: String,
                   RequestParam("date") DateTimeFormat(pattern = "dd/MM/yyyy") dateParam: LocalDate,
                   RequestParam("description") descriptionParam: String): String {
        var petOwnerId: String? = null
        db.withSession {
            Visits.insert(Visit(dateParam, descriptionParam, Id(petIdParam)))
            petOwnerId = Pets.select { ownerId }.get(Id(petIdParam)).value
        }
        return "redirect:/owners/view?id=${petOwnerId!!}"
    }

    RequestMapping(value = array("/edit"), method = array(RequestMethod.GET))
    public fun edit(RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val visit = Visits.get(Id(idParam))
            model.addAttribute("id", visit.id!!.value)
            model.addAttribute("date", visit.visitDate)
            model.addAttribute("description", visit.description)
            val pet = Pets.get(visit.petId)
            model.addAttribute("pet", pet)
            val owner = Owners.get(pet.ownerId)
            model.addAttribute("owner", owner)
        }
        return "visits/edit"
    }

    RequestMapping(value = array("/edit"), method = array(RequestMethod.POST))
    public fun edit(RequestParam("id") idParam: String,
                   RequestParam("date") DateTimeFormat(pattern = "dd/MM/yyyy") dateParam: LocalDate,
                   RequestParam("description") descriptionParam: String): String {
        var petOwnerId: String? = null
        db.withSession {
            Visits.select { visitDate + description }.find(Id(idParam)).set(dateParam, descriptionParam)
            val petId = Visits.select { petId }.get(Id(idParam))
            petOwnerId = Pets.select { ownerId }.get(petId).value
        }
        return "redirect:/owners/view?id=${petOwnerId!!}"
    }
}