package com.group6.not_my_fitness_pal.person;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

        private PersonService personService;

        public PersonController(PersonService personService) {
            this.personService = personService;
        }


//        // NOTE new springboot - has @ResponseBody embedded in @GetMapping annotation!
//        @GetMapping(path = "cars")
//        public List<Car> getCars() {
//            return carService.getCars();
//        }





}
