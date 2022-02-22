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
    // This will perform business logic for the FoodDao interface

    private FoodDao foodDao;
    private PersonService personService;
    //Properties created so that we can use the FoodDao interface and PersonService

    public FoodService(@Qualifier("food_postgres") FoodDao foodDao, PersonService personService){
        // Qualifier used to indicate the implementation we want to use
        this.foodDao = foodDao;
        this.personService = personService;
    }

    public int addFoodEntry(Food food) {
        // getPersonById checks if person_id is null and also if person in DB exists
        Person person = personService.getPersonById(food.getPerson_id());

        checkFoodInputProperties(food);
        // Add food entry to sql db - using FoodDao.  (Mock)
        int rowsAffected = foodDao.addFood(food);
        if (rowsAffected != 1){
            throw new IllegalStateException("Could not add food...");
        }
        return rowsAffected;
    }


    public int deleteFood(Integer foodId){
        Food foodInDb = getFoodOrThrowNull(foodId);

        int rowsAffected = foodDao.deleteFoodById(foodId);
        if (rowsAffected!=1){
            throw new IllegalStateException("Food could not be deleted");
        }
        return rowsAffected;
    }

    public int updateFood(Integer foodId, Food updateFood){
        Food foodInDb = getFoodOrThrowNull(foodId);
        checkFoodInputProperties(updateFood); //checks completed

        int rowsAffected = foodDao.updateFoodById(foodId, updateFood);
        if (rowsAffected!=1){
            throw new IllegalStateException("Food could not be updated");
        }
        return rowsAffected;
    }


    public Food getFoodById(Integer id){
        return getFoodOrThrowNull(id);
    }


    public List<Food> getAllFoodEntries (){
        List<Food> foodList = foodDao.getAllFood();
        if (foodList==null){
            throw new InvalidRequestException("no food entries found");
        }
        return foodList;
    }


    public List<Food> getFoodEntriesByPersonId (Integer person_id){
        Person personInDb = personService.getPersonById(person_id);
        List<Food> foodList = foodDao.getFoodEntriesByPersonId(personInDb.getId());
        if (foodList==null){
            throw new InvalidRequestException("no food entries found for person");
        }
        return foodList;
    }

    public List<Food> getFoodEntriesByPersonIdByWeek (Integer person_id, Integer week){
        Person personInDb = personService.getPersonById(person_id);
        List<Food> foodList = foodDao.getFoodEntriesByPersonIdByWeek(personInDb.getId(), week);
        if (foodList==null){
            throw new InvalidRequestException("no food entries found for person for that week");
        }
        return foodList;
    }
    public List<Food> getFoodEntriesByPersonIdByWeekByDay (Integer person_id, Integer week, Day day){
        Person personInDb = personService.getPersonById(person_id);
        List<Food> foodList = foodDao.getFoodEntriesByPersonIdByWeekByDay(personInDb.getId(), week, day);
        if (foodList==null){
            throw new InvalidRequestException("no food entries found for person for that week and day");
        }
        return foodList;
    }

    public List<Food> getFoodEntriesByMealType (MealType mealType){

        List<Food> foodList = foodDao.getFoodEntriesByMealType(mealType);
        if (foodList==null){
            throw new InvalidRequestException("no food entries found for that meal type");
        }
        return foodList;
    }


    private void checkFoodInputProperties(Food food) {
        if(food.getName() == null){
            throw new InvalidRequestException("name cannot be null");
        }
        if (food.getCalories()==null){
            throw new InvalidRequestException("calories cannot be null");
        }
        if (food.getCalories() < 0){
            throw new InvalidRequestException("calories cannot be negative");
        }
        if (food.getWeek() == null){
            throw new InvalidRequestException("week cannot be null");
        }
        if (food.getWeek() <= 0){
            throw new InvalidRequestException("invalid week");
        }
    }

    private Food getFoodOrThrowNull(Integer id){
        if (id == null || id < 0){
            throw new FoodNotFoundException("id is invalid");
        }

        // This is the scenario where argument capture would help - makes sure id persists throughout
        //id = 25;  // Ignore - we were testing this for scenario mentioned on the line above
        Food food = foodDao.getFoodById(id); //mocking this line

        if(food == null){
            throw new FoodNotFoundException("Food with id " + id + " doesn't exist");
        }
        return food;
    }
}
