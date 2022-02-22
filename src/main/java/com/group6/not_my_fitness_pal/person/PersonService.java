package com.group6.not_my_fitness_pal.person;

import com.group6.not_my_fitness_pal.InvalidRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    // This will perform business logic for


    // This is a property
    private PersonDao personDao;

    public PersonService(@Qualifier("person_postgres") PersonDao personDao){
        // Setting property of PersonService
        this.personDao = personDao;
    }

    public List<Person> getAllPeople (){
        return personDao.getAllPeople();
    }

    public Person getPersonById(Integer id){
        // It will return that person ELSE Exception in
        return getPersonOrThrowNull(id);
    }

    public int addPerson(Person person){
        checkPeopleInputProperties(person);

        Integer rowsAffected = personDao.addPerson(person); // addPerson will return the number of rows affected as
        // it is a sql implemented method. We created a variable for readability of this happening.
        if (rowsAffected != 1) {
            throw new IllegalStateException("Could not add person...");
        }
        return rowsAffected;
    }

    public int deletePersonById(Integer id) {
        Person personInDb = getPersonById(id);
        Integer rowsAffected = personDao.deletePersonById(personInDb.getId());

        if (rowsAffected != 1) {
            throw new IllegalStateException("Could not delete person...");
        }
        return rowsAffected;
    }

    public int updatePersonById(Integer id, Person updatePerson) {
        Person personInDb = getPersonOrThrowNull(id);   // Check person exists
        checkPeopleInputProperties(updatePerson);   // Check against our set of criteria for person entry properties,
        // since update method will require a new person entry

        Integer rowsAffected = personDao.updatePersonById(personInDb.getId(), updatePerson);
        if (rowsAffected != 1) {
            throw new IllegalStateException("Could not update person...");
        }
        return rowsAffected;
    }


    // REUSABLE FOR ALL OTHERS!! - This is checking person (Private - other public methods use it ONLY)
    //TODO: 2) Create Tests for this and getPersonById
    private Person getPersonOrThrowNull(Integer id){

        if (id == null || id < 0){
            throw new PersonNotFoundException("id is invalid");
        }

        // This is the scenario where argument capture would help - makes sure id persists throughout
//         id = 25;
        Person person = personDao.getPersonById(id); //mocking this line

        if(person == null){
            throw new PersonNotFoundException("Person with id " + id + " doesn't exist");
        }
        return person;
    }

    private void checkPeopleInputProperties(Person person) {
        if(person.getName() == null) {
            throw new InvalidRequestException("name cannot be null");
        }

        if(person.getAge() == null) {
            throw new InvalidRequestException("age cannot be null");
        }

        if(person.getAge() <= 0) {
            throw new InvalidRequestException("age cannot be less than or equal to 0");
        }

        if(person.getHeight_in_cm() <= 0) {
            throw new InvalidRequestException("height cannot be less than or equal to 0");
        }

        if(person.getWeight_in_kg() <= 0) {
            throw new InvalidRequestException("weight cannot be less than or equal to 0");
        }

        if(person.getCalorie_target() <= 0) {
            throw new InvalidRequestException("calorie target cannot be less than or equal to 0");
        }
    }
}
