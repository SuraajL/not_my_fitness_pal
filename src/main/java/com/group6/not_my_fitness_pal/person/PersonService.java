package com.group6.not_my_fitness_pal.person;

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


    // REUSABLE FOR ALL OTHERS!! - This is checking person (Private - other public methods use it ONLY)
    //TODO: 2) Create Tests for this and getPersonById
    private Person getPersonOrThrowNull(Integer id){

        if (id == null || id < 0){
            throw new IllegalStateException("id is invalid");
        }


        Person person = personDao.getPersonById(id); //mocking this line

        if(person == null){
            throw new IllegalStateException("Person with id "+ id +" doesn't exists");
        }
        return person;
    }

}
