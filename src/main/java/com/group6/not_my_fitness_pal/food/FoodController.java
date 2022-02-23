package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonDailyCalorieGoal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FoodController {

    private FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping(path = "food")
    public void addFood(@RequestBody Food food) {
        foodService.addFoodEntry(food);
    }

    @GetMapping(path = "getAllFood")
    public List<Food> foodList() {
        return foodService.getAllFoodEntries();
    }

    @DeleteMapping(path = "food/{id}")
    public void deleteFoodById(@PathVariable("id") Integer foodId) {
        foodService.deleteFood(foodId);
    }

    @PutMapping(path = "food/{id}")
    public void updateFoodById(@PathVariable("id") Integer foodId, @RequestBody Food update) {
        foodService.updateFood(foodId, update);
    }

    @GetMapping(path = "food/{id}")
    public Food getFoodById(@PathVariable("id") Integer foodId){
       return foodService.getFoodById(foodId);
    }

    @GetMapping(path = "food/person/{id}")
    public List<Food> getFoodEntriesByPersonId(@PathVariable("id") Integer personId){
        return foodService.getFoodEntriesByPersonId(personId);
    }

    @GetMapping(path = "food/person/{id}/week/{week}")
    public List<Food> getFoodEntriesByPersonIdByWeek(@PathVariable("id") Integer personId,@PathVariable("week") Integer week){
        return foodService.getFoodEntriesByPersonIdByWeek(personId, week);
    }

    @GetMapping(path = "food/person/{id}/week/{week}/day/{day}")
    public List<Food> getFoodEntriesByPersonIdByWeekByDay(@PathVariable("id") Integer personId,@PathVariable("week") Integer week, @PathVariable("day") Day day){//should day be string
        return foodService.getFoodEntriesByPersonIdByWeekByDay(personId, week, day);
    }

    @GetMapping(path = "food/person/mealtype/{mealtype}")
    public List<Food> getFoodEntriesByMealType(@PathVariable("mealtype") String mealType){//should day be string
        return foodService.getFoodEntriesByMealType(MealType.valueOf(mealType)); //converts string input for mealType into an ENUM
    }


    // Stretch Goal - GetDailyCalorieGoalsByWeekByDay
    @GetMapping(path = "food/calorie_goals/week/{week}/day/{day}")
    public List<PersonDailyCalorieGoal> getDailyCalorieGoalsByWeekByDay(@PathVariable("week") Integer week, @PathVariable("day") Day day){
        PersonDailyCalorieGoal p1Goal = new PersonDailyCalorieGoal(1, "mark", 2000, 1, Day.MONDAY, 1800);
        PersonDailyCalorieGoal p2Goal = new PersonDailyCalorieGoal(2, "Nasir", 2500, 1, Day.MONDAY, 2800);
        List<PersonDailyCalorieGoal> calorieGoals = new ArrayList<>();
        calorieGoals.add(p1Goal);
        calorieGoals.add(p2Goal);

        for (PersonDailyCalorieGoal calorieGoal : calorieGoals) {
            Integer calorie_difference = calorieGoal.getCalorie_target() - calorieGoal.getTotal_calories_on_week_on_day();
            calorieGoal.setCalorie_difference(calorie_difference);
            if (calorie_difference > 0){
                calorieGoal.setCalorie_goal_result(calorieGoal.getName() + " is " + Math.abs(calorie_difference) + " calories below their target." );

            } else if (calorie_difference < 0){
                calorieGoal.setCalorie_goal_result(calorieGoal.getName() + " is " + Math.abs(calorie_difference) + " calories above their target." );

            } else {
                calorieGoal.setCalorie_goal_result(calorieGoal.getName() +  " has met their daily calorie target of " + calorieGoal.getCalorie_target() + ".");
            }
        }
        return calorieGoals;
//        return foodService.getDailyCalorieGoalsByWeekByDay(week, day);
    }


}
