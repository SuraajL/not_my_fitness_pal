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


    // REUSEABLE FOR ALL OTHERS!! - This is checking person (Private - other public methods use it ONLY)
    private Person getPersonOrThrowNull(Integer id){
        Person person = personDao.getPersonById(id);

        if(person == null){
            throw new IllegalStateException("Person with id "+ id +" doesn't exists");
        }

        return person;
    }


}
