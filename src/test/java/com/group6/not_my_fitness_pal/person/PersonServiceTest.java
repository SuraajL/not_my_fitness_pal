package com.group6.not_my_fitness_pal.person;

import com.group6.not_my_fitness_pal.InvalidRequestException;
import com.group6.not_my_fitness_pal.food.Day;
import com.group6.not_my_fitness_pal.food.Food;
import com.group6.not_my_fitness_pal.food.MealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PersonServiceTest {
    private PersonDao personDao;
    private PersonService underTest;
    @BeforeEach
    void setUp(){
        personDao = Mockito.mock(PersonDao.class); //personDao property is set to mock of PersonDao class
        underTest = new PersonService(personDao); //underTest is set to a new instance of PersonService which uses the mocked interface
        // Can have a person here if you're using it all the time
    }

    // ================================= TESTS FOR getAllPeople =====================================

    @Test
    void canGetAllPeople() {
        //given
        Person person1 = new Person(1, "mark", 31, 175.0, 65.0, 2500);
        Person person2 = new Person(2, "sarah", 33, 165.0, 60.0, 2500);
        List<Person> expectedPeopleList = new ArrayList<>();
        expectedPeopleList.add(person1);
        expectedPeopleList.add(person2);
        given(personDao.getAllPeople()).willReturn(expectedPeopleList);
        //when
        List<Person> actualPeopleList = underTest.getAllPeople();
        //then
        assertThat(expectedPeopleList).isEqualTo(actualPeopleList);
    }

    @Test
    void shouldThrowWhenPersonDbIsEmpty(){
        //given
        given(personDao.getAllPeople()).willReturn(null);
        // when
        //then
        assertThatThrownBy(() -> underTest.getAllPeople())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not get list of people...");


    }

    // ================================= TESTS FOR getPersonById =====================================
    @Test
    void canGetPersonById() {
        //Given
        Integer id = 1;
        Person expected = new Person(id, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(id)).willReturn(expected);
        //When
        Person actual = underTest.getPersonById(id);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotGetPersonByIdWhenIdIsNull() {
        //Given
        Integer id = null;

        //When
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("id is invalid");
        //Then
        verify(personDao, never()).getPersonById(anyInt());
    }

    @Test
    void shouldNotGetPersonByIdWhenIdIsLessThanZero() {
        //Given
        Integer id = -1;
        //above line is in case getPersonById is called after the exception (but it shouldnt be)
        //When
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("id is invalid");
        //Then
        verify(personDao, never()).getPersonById(anyInt());
    }

    @Test
    void shouldNotGetPersonByIdWhenPersonIdDoesNotExist() {
        //Given
        Integer id = 100; //person with id 100 does not exist within the db
        given(personDao.getPersonById(id)).willReturn(null);
        //above line is to return null when calling id is 100 - kinda redundant!!

        //When
        //Then
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("Person with id " + id + " doesn't exist");

    }

    // ================================= TESTS FOR addPerson =====================================

    // ================================= TESTS FOR deletePersonById =====================================


    @Test
    void canDeletePersonById(){
        //Given
        Integer id = 1;
        given(personDao.getPersonById(id)).willReturn(new Person(1, "Mark", 2, 154.1, 55.1, 2000));
        given(personDao.deletePersonById(id)).willReturn(1);

        //When
        Integer actual = underTest.deletePersonById(id);

        //Then
        Integer expected =1;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenPersonNotDeleted(){
        //given
        Integer id = 1;
        given(personDao.getPersonById(id)).willReturn(new Person(1, "Mark", 23, 154.1, 55.1, 2000));
        given(personDao.deletePersonById(id)).willReturn(0);

        //When
        //Then
        assertThatThrownBy(()-> underTest.deletePersonById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not delete person...");


    }
    //nb does testing getpersonById mean I dont have to do should not delete when person is negative/null
    //do we need to ensure the person object persists throughout? ie person we put in is the same object we get out



    // ================================= TESTS FOR updatePersonById =====================================
    // When referring to personId, it is to do with a person in the database!
    // We are not interested in the updatePerson's Id as we do not use it anywhere (SQL takes care of it)

    @Test
    void shouldUpdatePersonById(){
        //Given
        Integer id = 1;
        Person personInDb = new Person(id, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(id)).willReturn(personInDb);
        Person updatePerson = new Person(id, "marcy", 24, 197.0, 97.0, 3000);
        given(personDao.updatePersonById(id, updatePerson)).willReturn(1);

        //When
        Integer actual = underTest.updatePersonById(id, updatePerson);

        //Then
        Integer expected = 1;
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void shouldThrowWhenPersonIsNotUpdatedById(){
        //Given
        Integer id = 1;
        Person personInDb = new Person(id, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(id)).willReturn(personInDb);
        Person updatePerson = new Person(null, "marcy", 24, 197.0, 97.0, 3000);
        given(personDao.updatePersonById(id, updatePerson)).willReturn(0);

        //When
        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(id, updatePerson))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not update person...");

    }


    @Test
    void shouldNotUpdateByPersonIdWhenPersonIdIsNegative(){
        //Given
        Integer personId = -1;
        Person updatePerson = new Person(null, "marcy", 24, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("person id is invalid");

        verify(personDao, never()).getPersonById(anyInt());
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateWhenPersonIdIsZeroWhenUpdatingPersonById(){
        //Given
        Integer PersonId = 0;
        Person updatePerson = new Person(null, "marcy", 24, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(PersonId, updatePerson))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("person id is invalid");

        verify(personDao, never()).getPersonById(anyInt());
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateByPersonIdWhenPersonInIDbDoesNotExist(){
        //Given
        Integer personInDbId = 1000;
        Person personInDb = null;
        given(personDao.getPersonById(personInDbId)).willReturn(personInDb);
        Person updatePerson = new Person(null, "marcy", 24, 197.0, 97.0, 3000);
        //We dont need updatePerson's id because sql will update the details for the id that we want

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personInDbId, updatePerson))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("Person with id " + personInDbId + " doesn't exist");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenPersonHasNullName(){
        //Given
        Integer personId = 1;
        String updateName = null;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, updateName, 24, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("name cannot be null");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateByPersonIdWhenPersonHasNullAge(){
        //Given
        Integer personId = 1;
        Integer updateAge = null;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", updateAge, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be null");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateByPersonIdWhenPersonHasNegativeAge(){
        //Given
        Integer personId = 1;
        Integer updateAge = -1;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", updateAge, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateByPersonIdWhenPersonHasZeroAge(){
        //Given
        Integer personId = 1;
        Integer updateAge = 0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", updateAge, 197.0, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdateByPersonIdWhenPersonHasNegativeHeight(){
        //Given
        Integer personId = 1;
        Double updateHeight = -1.0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, updateHeight, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("height cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenUpdatePersonHasZeroHeight(){
        //Given
        Integer personId = 1;
        Double updateHeight = 0.0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, updateHeight, 97.0, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("height cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenUpdatePersonHasNegativeWeight(){
        //Given
        Integer personId = 1;
        Double updateWeight = -1.0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, 167.0, updateWeight, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("weight cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenUpdatePersonHasZeroWeight(){
        //Given
        Integer personId = 1;
        Double updateWeight = 0.0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, 167.0, updateWeight, 3000);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("weight cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenPersonHasNegativeCalorieTarget(){
        //Given
        Integer personId = 1;
        Integer calorieTarget = -1000;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, 167.0, 57.0, calorieTarget);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calorie target cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

    @Test
    void shouldNotUpdatePersonByIdWhenPersonHasZeroCalorieTarget(){
        //Given
        Integer personId = 1;
        Integer calorieTarget = 0;
        Person personInDb = new Person(personId, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(personId)).willReturn(personInDb);
        Person updatePerson = new Person(personId, "marcy", 23, 167.0, 57.0, calorieTarget);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(personId, updatePerson))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calorie target cannot be less than or equal to 0");
        verify(personDao, never()).updatePersonById(anyInt(), any());
    }

}