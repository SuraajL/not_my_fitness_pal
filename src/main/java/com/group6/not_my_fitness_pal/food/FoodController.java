package com.group6.not_my_fitness_pal.food;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {

    private FoodService foodService;

    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @PostMapping(path = "food")
    public void addFood (@RequestBody Food food){
        foodService.addFoodEntry(food);
    }

    @GetMapping(path = "getAllFood")
    public List<Food> foodList(){
        return foodService.getAllFoodEntries();
    }

}
