package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonDao;
import com.group6.not_my_fitness_pal.person.PersonNotFoundException;
import com.group6.not_my_fitness_pal.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(1);

        //When
        Integer actual = underTest.addFoodEntry(food);



        //Then
        // Assert the integer is 1
        Integer expected = 1;
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void foodShouldPersistWhenAddingFoodEntry() {
        // The whole purpose of this is to tell developer if food correctly Persists throughout
        //Given
        Food food = new Food(1, 1, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(1);

        //When
        // No need to store integer - Only testing food being saved
        underTest.addFoodEntry(food);


        //Then
        // This is to make sure the food being added is the one we passed in first
        ArgumentCaptor<Food> foodArgumentCaptor = ArgumentCaptor.forClass(Food.class);
        verify(foodDao).addFood(foodArgumentCaptor.capture());
        Food actualFoodSaved = foodArgumentCaptor.getValue();
        assertThat(actualFoodSaved).isEqualTo(food);
        //assertThat(food.getPerson_id()).isEqualTo(personInDb.getId());  // Is this necessary?

    }

    @Test
    void foodPersonIdShouldPersistWhenAddingFoodEntry() {
        // The whole purpose of this is to tell developer if person_id (property of Food) correctly Persists throughout
        //Given
        Food food = new Food(1, 1, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(1);

        //When
        // No need to store integer - Only testing if person_id from Food property is being used by getPersonById
        underTest.addFoodEntry(food);

        //Then
        // This is checking that the id passed into personDao.getPersonById is the same as the food.getPerson_id
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(personDao).getPersonById(integerArgumentCaptor.capture());
        Integer actualIdPassedIntoPersonDao = integerArgumentCaptor.getValue();
        assertThat(actualIdPassedIntoPersonDao).isEqualTo(food.getPerson_id());
    }


    @Test
    void shouldNotAddWhenPersonIdIsNull() {
        // The whole purpose of this is to tell developer if person_id (property of Food) correctly Persists throughout
        //Given
        // NOTE: person_id is null inside Food property
        Food food = new Food(1, null, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(null);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("id is invalid");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).getPersonById(anyInt());
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenNameIsNull() {
        //Given
        // NOTE: name is null inside Food property
        Food food = new Food(1, 1, null, MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("name cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenCaloriesIsNull() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "mark", MealType.BREAKFAST, "random", null, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("calories cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenCaloriesIsNegative() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "mark", MealType.BREAKFAST, "random", -1, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("calories cannot be negative");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }


    @Test
    void shouldNotAddWhenWeekIsNull() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "mark", MealType.BREAKFAST, "random", 100, null, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("week cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenWeekIsNegative() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "mark", MealType.BREAKFAST, "random", 100, -1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("invalid week");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenWeekIsZero() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "mark", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        given(foodDao.addFood(food)).willReturn(0); //Since we should never call it - doesn't matter what we return

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("invalid week");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }
    @Test
    void getFoodEntriesByPersonId() {

    }

}