package com.group6.not_my_fitness_pal.person;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

        private PersonService personService;

        public PersonController(PersonService personService) {
            this.personService = personService;
        }





        @GetMapping(path = "people")
        public List<Person> getPeople() {
            return personService.getAllPeople();
        }





}
