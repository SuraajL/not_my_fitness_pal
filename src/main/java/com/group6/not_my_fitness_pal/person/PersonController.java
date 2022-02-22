package com.group6.not_my_fitness_pal.person;

import com.group6.not_my_fitness_pal.food.Food;
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

    @GetMapping(path = "people/{id}")
    public Person getPersonById(@PathVariable("id") Integer personId) {
        return personService.getPersonById(personId);
    }

    @PostMapping(path = "person")
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    @DeleteMapping(path = "person/{id}")
    public void deletePersonById(@PathVariable("id") Integer personId) {
        personService.deletePersonById(personId);
    }

    @PutMapping(path = "person/{id}")
    public void updatePersonById(@PathVariable("id") Integer personId, @RequestBody Person update) {
        personService.updatePersonById(personId, update);
    }

}
