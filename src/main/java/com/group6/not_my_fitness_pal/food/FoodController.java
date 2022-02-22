package com.group6.not_my_fitness_pal.food;

import org.springframework.web.bind.annotation.*;

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

}
