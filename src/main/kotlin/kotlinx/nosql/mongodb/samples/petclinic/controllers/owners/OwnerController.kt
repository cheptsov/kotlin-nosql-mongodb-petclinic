package kotlinx.nosql.mongodb.samples.petclinic.controllers.owners

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import kotlinx.nosql.mongodb.*
import kotlinx.nosql.*
import kotlinx.nosql.mongodb.*
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
import kotlinx.nosql.mongodb.samples.petclinic.data.Visit
import kotlinx.nosql.mongodb.samples.petclinic.data.Visits

@Controller @RequestMapping(value = "/owners")
class OwnerController @Autowired constructor (val db: MongoDB) {

    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST))
    fun add(@RequestParam("firstName") firstName: String,
                   @RequestParam("lastName") lastName: String,
                   @RequestParam("address") address: String,
                   @RequestParam("city") city: String,
                   @RequestParam("telephone") telephone: String): String {
        db.withSession {
            Owners.insert(Owner(firstName, lastName, address, city, telephone))
        }
        return "redirect:/owners/";
    }

    @RequestMapping(value = "/edit", method = arrayOf(RequestMethod.POST))
    fun edit(@RequestParam("id") idParam: String,
                    @RequestParam("firstName") firstNameParam: String,
                    @RequestParam("lastName") lastNameParam: String,
                    @RequestParam("address") addressParam: String,
                    @RequestParam("city") cityParam: String,
                    @RequestParam("telephone") telephoneParam: String): String {
        db.withSession {
            Owners.find{ id.equal(Id(idParam))}.projection { firstName + lastName + address + city + telephone }.update(firstNameParam, lastNameParam, addressParam, cityParam, telephoneParam)
        }
        return "redirect:/owners/";
    }

    @RequestMapping("/add", method = arrayOf(RequestMethod.GET))
    fun add(): String {
        return "owners/add"
    }

    @RequestMapping("/edit", method = arrayOf(RequestMethod.GET))
    fun edit(@RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val owner = Owners.find{ id.equal(Id(idParam))}.single()
            model.addAttribute("id", owner.id!!.value)
            model.addAttribute("firstName", owner.firstName)
            model.addAttribute("lastName", owner.lastName)
            model.addAttribute("address", owner.address)
            model.addAttribute("city", owner.city)
            model.addAttribute("telephone", owner.telephone)
        }
        return "owners/edit"
    }

    @RequestMapping("/view", method = arrayOf(RequestMethod.GET))
    fun view(@RequestParam("id") idParam: String, model: Model): String {
        db.withSession {
            val owner = Owners.find { id.equal(Id(idParam)) }.single()
            model.addAttribute("owner", owner)
            val pets = Pets.find { ownerId.equal(owner.id)}.toList()

            val petTypes = hashMapOf<Pet,PetType>()
            val petVisits = hashMapOf<Pet, List<Visit>>()
            pets.forEach {
                petTypes.put(it, PetTypes.find { id.equal(it.typeId) }.single())
                petVisits.put(it, Visits.find { petId.equal(it.id) }.toList())
            }
            model.addAttribute("pets", pets)
            model.addAttribute("petTypes", petTypes)
            model.addAttribute("petVisits", petVisits)
        }
        return "owners/view"
    }

    @RequestMapping("/delete", method = arrayOf(RequestMethod.POST))
    fun delete(@RequestParam("id") idParam: String): String {
        db.withSession {
            Owners.find { id.equal(Id(idParam)) }.remove()
        }
        return "redirect:/owners/";
    }

    @RequestMapping("/", method = arrayOf(RequestMethod.GET))
    fun index(@RequestParam("q", required = false) searchParam: String?, model: Model): String {
        model.addAttribute("searchQuery", if (searchParam != null) searchParam else "")
        db.withSession {
            if (searchParam != null && searchParam.isNotEmpty()) {
                model.addAttribute("owners", Owners.find { text(searchParam) }.map { owner -> Pair(owner, Pets.find { ownerId.equal(owner.id) }.toList())}.toList())
            } else {
                model.addAttribute("owners", Owners.find().map { owner -> Pair(owner, Pets.find { ownerId.equal(owner.id) }.toList())}.toList())
            }
        }
        return "owners/index"
    }
}