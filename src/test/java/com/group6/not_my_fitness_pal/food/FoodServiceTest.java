package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.InvalidRequestException;
import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonDao;
import com.group6.not_my_fitness_pal.person.PersonNotFoundException;
import com.group6.not_my_fitness_pal.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
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
        // we get one back - but don't know what we're getting back

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
        // The number returned - doesn't give us any idea of what food is being saved

        //When
        // No need to store integer - Only testing food being saved
        underTest.addFoodEntry(food);

        //Then
        // This is to make sure the food being added is the one we passed in first
        ArgumentCaptor<Food> foodArgumentCaptor = ArgumentCaptor.forClass(Food.class);
        verify(foodDao).addFood(foodArgumentCaptor.capture());
        Food actualFoodSaved = foodArgumentCaptor.getValue();
        assertThat(actualFoodSaved).isEqualTo(food);
    }


    @Test
    void shouldThrowExceptionIfAddFoodEntryFails() {
        //Given
        Food food = new Food(1, 1, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);
        // Return 0 to imitate failed entry
        given(foodDao.addFood(food)).willReturn(0);

        //When
        //Then
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not add food...");


    }



    // TESTS FOR checkFoodInputProperties!!! - separate it from the other one!!

    @Test
    void shouldNotAddWhenPersonIdIsNull() {
        // The whole purpose of this is to tell developer if person_id (property of Food) correctly Persists throughout
        //Given
        // NOTE: person_id is null inside Food property
        Food food = new Food(1, null, "toast", MealType.BREAKFAST, "random", 50, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);

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

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("name cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenCaloriesIsNull() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", null, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calories cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenCaloriesIsNegative() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", -1, 1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calories cannot be negative");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }


    @Test
    void shouldNotAddWhenWeekIsNull() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", 100, null, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("week cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenWeekIsNegative() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", 100, -1, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("invalid week");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }

    @Test
    void shouldNotAddWhenWeekIsZero() {
        //Given
        // NOTE: calories is null inside Food property
        Food food = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        // we pass in person Id using food.getPerson_id (getter for Food Class - as personId is a property of it)"
        // DO WE NEED THESE SINCE WE DON'T ACTUALLY USE THEM?? SEE VERIFY AT BOTTOM
        Person personInDb = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(food.getPerson_id())).willReturn(personInDb);

        //When
        assertThatThrownBy(() -> underTest.addFoodEntry(food))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("invalid week");

        //Then
        // Verify that none of these methods are called
        verify(foodDao, never()).addFood(any());
    }






    // TESTS FOR getAllFood ===================
    @Test
    void shouldGetAllFood() {
        //given
        Food food1 = new Food(1, 1, "cereal", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        Food food2 = new Food(2, 1, "pancakes", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        List<Food> expectedFoodList = new ArrayList<>();
        expectedFoodList.add(food1);
        expectedFoodList.add(food2);

        given(foodDao.getAllFood()).willReturn(expectedFoodList);

        //when
        List<Food> actualFoodList = underTest.getAllFoodEntries();

        //then
        assertThat(expectedFoodList).isEqualTo(actualFoodList);

    }


    @Test
    void shouldThrowWhenFoodDbIsEmpty() {
        //given
        given(foodDao.getAllFood()).willReturn(null);

        //When
        assertThatThrownBy(() -> underTest.getAllFoodEntries())
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("no food entries found");


    }

    @Test
    void shouldDeleteFoodById(){
        //given
        Integer id = 1;
        given (foodDao.getFoodById(id)).willReturn(new Food(1, 1, "cereal", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY));
        given(foodDao.deleteFoodById(id)).willReturn(1);

        //when

        Integer actual = underTest.deleteFood(id);

        //then
        Integer expected = 1;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenFoodNotDeleted(){
        //given
        Integer id = 1;
        given (foodDao.getFoodById(id)).willReturn(new Food(1, 1, "pizza", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY));
        given(foodDao.deleteFoodById(id)).willReturn(0);
        //when
        assertThatThrownBy(() -> underTest.deleteFood(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Food could not be deleted");
        //then

    }

    @Test
    @Disabled
    void shouldNotDeleteWhenIdIsNull(){ //TODO does testing getfoodById in isolation mean this is redundant
        //if it is redundant how do we verify that we never interact with deletefood
        //given
        Integer id = null;

        //when
        assertThatThrownBy(() -> underTest.deleteFood(id))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Food id is invalid");
        //then
        verify(foodDao, never()).getFoodById(anyInt());
        verify(foodDao, never()).deleteFoodById(anyInt());
    }

    @Test
    @Disabled
    void shouldNotDeleteWhenIdIsNegative(){
//same as above, is this redundant?
    }

    @Test
    void shouldGetFoodById(){
        //Given
        Integer id = 1;
        Food expected = new Food(1, 1, "chips", MealType.DINNER, "random", 100, 0, Day.MONDAY);
        given(foodDao.getFoodById(id)).willReturn(expected);
        //When
        Food actual = underTest.getFoodById(id);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotGetFoodWhenIdIsNull(){
        //given
        Integer id = null;

        //when
        assertThatThrownBy(() -> underTest.getFoodById(id))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Food id is invalid");
        //then
        verify(foodDao, never()).getFoodById(anyInt());
    }
    @Test
    void shouldNotGetFoodWhenIdIsNegative(){
        //given
        Integer id = -1;

        //when
        assertThatThrownBy(() -> underTest.getFoodById(id))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Food id is invalid");
        //then
        verify(foodDao, never()).getFoodById(anyInt());
    }

    @Test
    void shouldNotGetFoodWhenFoodIsNotInDb(){
        //given
        Integer id = 100;
        given(foodDao.getFoodById(id)).willReturn(null);

        //when
        //then
        assertThatThrownBy(() -> underTest.getFoodById(id))
                .isInstanceOf(FoodNotFoundException.class)
                .hasMessageContaining("Food with id " + id + " doesn't exist");
    }

    @Test
    void shouldUpdateFoodById(){
        //Given
        Integer id = 1;
        Food foodInDb = new Food(1, 1, "chips", MealType.DINNER, "random", 100, 1, Day.MONDAY);
        given(foodDao.getFoodById(id)).willReturn(foodInDb);
        Food updateFood = new Food(1, 1, "pizza", MealType.DINNER, "random", 100, 1, Day.MONDAY);
        given(foodDao.updateFoodById(id, updateFood)).willReturn(1);

        //When
        Integer actual = underTest.updateFood(id, updateFood);

        //Then
        Integer expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Disabled
    void shouldNotUpdateFoodByIdWhenIdIsNull(){ //TODO redundant?
    }

    @Test
    @Disabled
    void shouldNotUpdateFoodByIdWhenIdIsNegative(){ //TODO redundant?
    }

    @Test
    void shouldThrowWhenFoodIsNotUpdatedById(){
        //Given
        Integer id = 1;
        Food foodInDb = new Food(1, 1, "chips", MealType.DINNER, "random", 100, 1, Day.MONDAY);
        given(foodDao.getFoodById(id)).willReturn(foodInDb);
        Food updateFood = new Food(1, 1, "pizza", MealType.DINNER, "random", 100, 1, Day.MONDAY);
        given(foodDao.updateFoodById(id, updateFood)).willReturn(0);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updateFood(id, updateFood))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Food could not be updated");
    }

    @Test
    void shouldGetFoodEntriesByPersonId(){
        //Given
        Integer person_id = 1;
        Person person = new Person(1, "mark", 23, 157.0, 47.0, 2000);
        Food food1 = new Food(1, person_id, "cereal", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        Food food2 = new Food(2, person_id, "pancakes", MealType.BREAKFAST, "random", 100, 0, Day.MONDAY);
        List<Food> expectedFoodList = new ArrayList<>();
        expectedFoodList.add(food1);
        expectedFoodList.add(food2);

        given(personDao.getPersonById(person_id)).willReturn(person);
        given(foodDao.getFoodEntriesByPersonId(person_id)).willReturn(expectedFoodList);

        //When
        List<Food> actualFoodList = underTest.getFoodEntriesByPersonId(person_id);

        //Then
        assertThat(actualFoodList).isEqualTo(expectedFoodList);
    }

    @Test
    void shouldThrowIfGetFoodEntriesByPersonIdReturnsNull(){
        //Given
        Integer person_id = 1;
        Person person = new Person(1, "mark", 23, 157.0, 47.0, 2000);

        given(personDao.getPersonById(person_id)).willReturn(person);
        given(foodDao.getFoodEntriesByPersonId(person_id)).willReturn(null);

        //When

        //Then
        assertThatThrownBy(() -> underTest.getFoodEntriesByPersonId(person_id))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("no food entries found for person");
    }



}