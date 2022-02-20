package com.group6.not_my_fitness_pal.person;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("person_postgres")
public class PersonDataAccessService implements PersonDao{

    @Override
    public List<Person> getAllPeople() {
        return null;
    }

    @Override
    public Person getPersonById(Integer id) {
        //TODO: DO THIS FIRST - This is needed for FoodService
        return null;
    }

    @Override
    public int addPerson(Person person) {
        return 0;
    }

    @Override
    public int deletePersonById(Integer id) {
        return 0;
    }

    @Override
    public int updatePersonById(Integer id, Person updatePerson) {
        return 0;
    }
}
