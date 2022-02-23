package com.group6.not_my_fitness_pal.person;

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

    // ================================= TESTS FOR deletePersonById =====================================

    // ================================= TESTS FOR updatePersonById =====================================

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
    void shouldThrowWhenFoodIsNotUpdatedById(){
        //Given
        Integer id = 1;
        Person personInDb = new Person(id, "marcy", 23, 157.0, 47.0, 2000);
        given(personDao.getPersonById(id)).willReturn(personInDb);
        Person updatePerson = new Person(id, "marcy", 24, 197.0, 97.0, 3000);
        given(personDao.updatePersonById(id, updatePerson)).willReturn(0);

        //When
        Integer actual = underTest.updatePersonById(id, updatePerson);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updatePersonById(id, updatePerson))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not update person...");
    }

}