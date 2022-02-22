package com.group6.not_my_fitness_pal.person;

import java.util.List;

public interface PersonDao {
    List<Person> getAllPeople();
    Person getPersonById(Integer id);
    int addPerson(Person person);
    int deletePersonById(Integer id);
    int updatePersonById(Integer id, Person updatePerson);

}
