package kotlinx.nosql.mongodb.samples.petclinic.controllers

import org.springframework.context.annotation.Configuration
import kotlinx.nosql.mongodb.MongoDB
import kotlinx.nosql.mongodb.samples.petclinic.data.*
import org.springframework.context.annotation.Bean
import com.mongodb.MongoClientURI
import kotlinx.nosql.CreateDrop
import org.joda.time.LocalDate

Configuration open class AppConfig {
    Bean open fun getMongoDB(): MongoDB {
        val mongoURI = MongoClientURI(System.getenv("MONGO_URI"))
        val mongoDb = MongoDB(mongoURI, schemas = array(Owners, Pets, PetTypes, Vets, Visits), initialization = CreateDrop(onCreate = {
            val birdId = PetTypes.insert(PetType("Bird"))
            val catId = PetTypes.insert(PetType("Cat"))
            val dogId = PetTypes.insert(PetType("Dog"))
            val hamsterId = PetTypes.insert(PetType("Hamster"))
            val lizardId = PetTypes.insert(PetType("Lizard"))
            val snakeId = PetTypes.insert(PetType("Snake"))

            val georgeId = Owners.insert(Owner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"))

            Pets.insert(Pet("Janny", LocalDate(2014, 2, 8), hamsterId, georgeId))
            Pets.insert(Pet("Leo", LocalDate(2000, 9, 7), catId, georgeId))
            Pets.insert(Pet("Shaka", LocalDate(2000, 3, 10), dogId, georgeId))

            val jeanId = Owners.insert(Owner("Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654"))

            Pets.insert(Pet("Max", LocalDate(1995, 9, 4), catId, jeanId))
            Pets.insert(Pet("Samantha", LocalDate(1995, 9, 4), catId, jeanId))

            val jeffreyId = Owners.insert(Owner("Jeffrey", "Black", "1450 Oak Blvd.", "Monona", "6085555387"))

            Pets.insert(Pet("Jeff", LocalDate(2014, 1, 21), dogId, jeffreyId))
            Pets.insert(Pet("Lucky", LocalDate(1999, 8, 6), birdId, jeffreyId))

            val mariaId = Owners.insert(Owner("Maria", "Escobito", "345 Maple St.", "Madison", "6085557682"))

            Pets.insert(Pet("Manitoba", LocalDate(1997, 2, 24), dogId, mariaId))

            val peterId = Owners.insert(Owner("Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765"))

            Pets.insert(Pet("George", LocalDate(2000, 1, 20), snakeId, peterId))

            val robertId = Owners.insert(Owner("Robert", "Schroeder", "2749 Blackhawk Trail", "6085559436", "6085552765"))

            Pets.insert(Pet("Freddy", LocalDate(2000, 3, 9), lizardId, robertId))
        }))
        return mongoDb
    }
}