package kotlinx.nosql.mongodb.samples.petclinic.controllers

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMapping

Controller
ComponentScan
EnableAutoConfiguration
class AppController {
    RequestMapping(value = array("/"))
    fun home(): String {
        return "redirect:/owners/"
    }
}

fun main(args: Array<String>) {
    System.setProperty("server.port", System.getenv("PORT")!!)
    SpringApplication.run(array(javaClass<AppController>()), args);
}
