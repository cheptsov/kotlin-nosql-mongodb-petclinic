package kotlinx.nosql.mongodb.samples.petclinic.controllers

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMapping
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import org.springframework.context.annotation.Bean

@Controller
@ComponentScan
@EnableAutoConfiguration
class AppController {
    @RequestMapping(value = "/")
    fun home(): String {
        return "redirect:/owners/"
    }

    @Bean
    fun templateResolver(): ServletContextTemplateResolver {
        val resolver = ServletContextTemplateResolver()
        resolver.setPrefix("/resources/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false);
        return resolver;

    }
}

fun main(args: Array<String>) {
    System.setProperty("server.port", System.getenv("PORT")!!)
    SpringApplication.run(arrayOf(AppController::class.java), args);
}
