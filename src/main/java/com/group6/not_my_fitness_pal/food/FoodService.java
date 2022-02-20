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



}
