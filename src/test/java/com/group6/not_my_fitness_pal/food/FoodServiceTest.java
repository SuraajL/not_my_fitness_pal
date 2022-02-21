package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonDao;
import com.group6.not_my_fitness_pal.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    void addFoodEntry() {
        //Given
        Food food = new Food(1, 1, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(1);
        //When
        Integer actual = underTest.addFoodEntry(food);
        //Then
        ArgumentCaptor<Food> foodArgumentCaptor = ArgumentCaptor.forClass(Food.class);
        verify(foodDao).addFood(foodArgumentCaptor.capture());
        Food actualFoodSaved = foodArgumentCaptor.getValue();
        assertThat(actualFoodSaved).isEqualTo(food);
        //assertThat(food.getPerson_id()).isEqualTo(personInDb.getId());  // Is this necessary?

        // This is checking that the id passed into personDao.getPersonById is the same as the food.getPerson_id
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(personDao).getPersonById(integerArgumentCaptor.capture());
        Integer actualIdPassedIntoPersonDao = integerArgumentCaptor.getValue();
        assertThat(actualIdPassedIntoPersonDao).isEqualTo(food.getPerson_id());
    }

    @Test
    void getFoodEntriesByPersonId() {

    }

}