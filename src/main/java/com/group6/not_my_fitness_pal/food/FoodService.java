package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.InvalidRequestException;
import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonNotFoundException;
import com.group6.not_my_fitness_pal.person.PersonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

// General framework for service methods
// First validate all inputs - e.g name not null, id not null - Note: GetPersonById checks not needed (see add food)
// Then make a call to the foodDao.Method
// Then validate if return FROM SQL is not as expected (!= 1 or == null Throw exception)


@Service
public class FoodService {

    private FoodDao foodDao;
    private PersonService personService;

    public FoodService(@Qualifier("food_postgres") FoodDao foodDao, PersonService personService){
        this.foodDao = foodDao;
        this.personService = personService;
    }

    public List<Food> getFoodEntriesByPersonId (Integer person_id){
//        personService.getPerson
        return null;
    }

    //TODO:3) Create Psuedo code for add food
    public int addFoodEntry(Food food) {
        // Check all fields are valid (enums don't have to be checked here):
        // person_id - use person service, if null then throw exception - using PersonDao. (Mock)
        // getPersonById checks if person_id is null and also if person in DB exists
        Person person = personService.getPersonById(food.getPerson_id());

        //    name - can't be null
        if(food.getName() == null){
            throw new InvalidRequestException("name cannot be null");
        }
        //    calories - can't be null or < 0 - 0 is accepted
        if (food.getCalories()==null){
            throw new InvalidRequestException("calories cannot be null");
        }
        if (food.getCalories() < 0){
            throw new InvalidRequestException("calories cannot be negative");
        }
        //    week - can't be null or <= 0
        if (food.getWeek() == null){
            throw new InvalidRequestException("week cannot be null");
        }

        if (food.getWeek() <= 0){
            throw new InvalidRequestException("invalid week");
        }

        // Add food entry to sql db - using FoodDao.  (Mock)
        // foodDao.addFood(someRandom)
        int rowsAffected = foodDao.addFood(food);

        // If result != 1, then throw exception to say it failed
        if (rowsAffected != 1){
            throw new IllegalStateException("Could not add food...");
        }

        return 1;
    }



}
