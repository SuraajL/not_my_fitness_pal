package com.group6.not_my_fitness_pal.food;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("food_postgres")
public class FoodDataAccessService implements FoodDao{

    @Override
    public int addFood(Food food) {
        return 0;
    }

    @Override
    public int deleteFoodById(Integer id) {
        return 0;
    }

    @Override
    public int updateFoodById(Integer id, Food update) {
        return 0;
    }

    @Override
    public Food getFoodById(Integer id) {
        return null;
    }

    @Override
    public List<Food> getAllFood() {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonId(Integer person_id) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeek(Integer person_id, Integer week) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeekByDay(Integer person_id, Integer week, Day day) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByMealType(MealType mealType) {
        return null;
    }
}
