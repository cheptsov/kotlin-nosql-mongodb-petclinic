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
            val pet = Pets.find { id.equal(Id(petIdParam)) }.single()
            model.addAttribute("pet", pet)
            val owner = Owners.find { id.equal(pet.ownerId) }.single()
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
            petOwnerId = Pets.find { id.equal(Id(petIdParam)) }.projection { ownerId }.single().value
        }
        return "redirect:/owners/view?id=${petOwnerId!!}"
    }

    RequestMapping(value = array("/edit"), method = array(RequestMethod.GET))
    public fun edit(RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val visit = Visits.find { id.equal(Id(idParam)) }.single()
            model.addAttribute("id", visit.id!!.value)
            model.addAttribute("date", visit.visitDate)
            model.addAttribute("description", visit.description)
            val pet = Pets.find { id.equal(visit.petId) }.single()
            model.addAttribute("pet", pet)
            val owner = Owners.find { id.equal(pet.ownerId) }.single()
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
            Visits.find { id.equal(Id(idParam)) }.projection { visitDate + description }.update(dateParam, descriptionParam)
            val petId = Visits.find { id.equal(Id(idParam)) }.projection { petId }.single()
            petOwnerId = Pets.find { id.equal(petId) }.projection { ownerId }.single().value
        }
        return "redirect:/owners/view?id=${petOwnerId!!}"
    }
}