package com.group6.not_my_fitness_pal.person;

import com.group6.not_my_fitness_pal.InvalidRequestException;
import com.group6.not_my_fitness_pal.food.Day;
import com.group6.not_my_fitness_pal.food.Food;
import com.group6.not_my_fitness_pal.food.MealType;
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
    }

    // ================================= TESTS FOR getPersonById =====================================
    @Test
    void canGetPersonById() {
        //Given
        Integer id = 1;
        Person expected = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
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

    @Test
    void addPerson() {
        //Given
        Person person = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.addPerson(person)).willReturn(1);
        // we get one back - but don't know what we're getting back - leads to next test

        //When
        Integer actual = underTest.addPerson(person);

        //Then
        // Assert the integer is 1
        Integer expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void personShouldPersistWhenAddingPerson() {
        // The whole purpose of this is to tell developer if person correctly persists throughout
        //Given
        Person person = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.addPerson(person)).willReturn(1);
        // The number returned - doesn't give us any idea of what specific person is being saved

        //When
        // No need to store integer - Only testing person being saved
        underTest.addPerson(person);

        //Then
        // This is to make sure the person being added is the one we passed in first
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personDao).addPerson(personArgumentCaptor.capture());
        Person actualPersonSaved = personArgumentCaptor.getValue();
        assertThat(actualPersonSaved).isEqualTo(person);
    }

    @Test
    void shouldThrowExceptionIfAddPersonFails() {
        //Given
        Person person = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        // Return 0 to imitate failed entry
        given(personDao.addPerson(person)).willReturn(0);

        //When
        //Then
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not add person...");
    }

    @Test
    void shouldNotAddWhenNameIsNull() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, null, 23, 157.0, 47.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("name cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenAgeIsNull() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", null, 157.0, 67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be null");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenAgeIsNegative() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", -1, 157.0, 67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenAgeIsZero() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 0, 157.0, 67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("age cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenHeight_in_cmIsNegative() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, -100.0, 67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("height cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenHeight_in_cmIsZero() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, 0.0, 67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("height cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenWeight_in_kgIsNegative() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, 180.0, -67.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("weight cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenWeight_in_kgIsZero() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, 180.0, 0.0, 2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("weight cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenCalorie_targetIsNegative() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, 180.0, 67.0, -2000);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calorie target cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    @Test
    void shouldNotAddWhenCalorie_targetIsZero() {
        //Given
        // NOTE: name is null inside Person property
        Person person = new Person(1, "Mike", 23, 180.0, 0.0, 0);

        //When
        assertThatThrownBy(() -> underTest.addPerson(person))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("calorie target cannot be less than or equal to 0");

        //Then
        // Verify that none of these methods are called
        verify(personDao, never()).addPerson(any());
    }

    // ================================= TESTS FOR deletePersonById =====================================

    // ================================= TESTS FOR updatePersonById =====================================

}