package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.PersonDao;
import com.group6.not_my_fitness_pal.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class FoodServiceTest {

    private PersonDao personDao;
    private PersonService personService;
    private FoodDao foodDao;
    private FoodService underTest;
    @BeforeEach
    void setUp(){
        personDao = Mockito.mock(PersonDao.class); //personDao property is set to mock of PersonDao class
        personService = new PersonService(personDao);
        foodDao = Mockito.mock(FoodDao.class); //foodDao property is set to mock of FoodDao class
        underTest = new FoodService(foodDao, personService); //underTest is set to a new instance of FoodService
        // which uses the mocked foodDao interface and personService (because we use getPersonById for foodService
        // methods)
    }

    @Test
    void getFoodEntriesByPersonId() {
        // Given

        // When

        // Then
    }

    @Test
    void addFoodEntry() {
        // Given

        // When

        // Then
    }
}