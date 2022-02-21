package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.PersonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //    person_id - use person service, if null then throw exception - using PersonDao. (Mock)
        //    name - can't be null
        //    calories - can't be null or < 0 - 0 is accepted
        //    week - can't be null or <= 0
        // Add food entry to sql db - using FoodDao.  (Mock)
        // foodDao.addFood(someRandom)
        // If result != 1, then throw exception to say it failed

        return 1;
    }



}
