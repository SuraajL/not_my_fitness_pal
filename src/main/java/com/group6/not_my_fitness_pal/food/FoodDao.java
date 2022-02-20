package com.group6.not_my_fitness_pal.food;

import java.util.List;

public interface FoodDao {
    int addFood(Food food);
    int deleteFoodById(Integer id);
    int updateFoodById(Integer id, Food update);
    Food getFoodById(Integer id);
    List<Food> getAllFood();
    List<Food> getFoodEntriesByPersonId(Integer person_id);
    List<Food> getFoodEntriesByPersonIdByWeek(Integer person_id, Integer week); //Leave this for later
    List<Food> getFoodEntriesByPersonIdByWeekByDay(Integer person_id, Integer week, Day day);
    List<Food> getFoodEntriesByMealType(MealType mealType);


}
