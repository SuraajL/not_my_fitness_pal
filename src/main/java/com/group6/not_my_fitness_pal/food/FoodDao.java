package com.group6.not_my_fitness_pal.food;

import java.util.List;


public interface FoodDao {
    int addFood(Food food); // MVP
    int deleteFoodById(Integer id); // MVP
    int updateFoodById(Integer id, Food update); // MVP
    Food getFoodById(Integer id); // MVP
    List<Food> getAllFood(); // MVP
    List<Food> getFoodEntriesByPersonId(Integer person_id); // Priority stretch
    List<Food> getFoodEntriesByPersonIdByWeek(Integer person_id, Integer week); //Leave this for later
    List<Food> getFoodEntriesByPersonIdByWeekByDay(Integer person_id, Integer week, Day day);
    List<Food> getFoodEntriesByMealType(MealType mealType);


}
