package com.group6.not_my_fitness_pal;

import com.group6.not_my_fitness_pal.food.Day;
import com.group6.not_my_fitness_pal.food.Food;
import com.group6.not_my_fitness_pal.food.MealType;
import com.group6.not_my_fitness_pal.person.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class NotMyFitnessPalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotMyFitnessPalApplication.class, args);
		System.out.println("Hello World");

		Person test = new Person(1, "me", 10, 168.8, 65.5, 2600);
		System.out.println(test);


		Food foodTest = new Food(1, 1, "Borgor", MealType.BREAKFAST, "I dont know why", 600, 1, Day.SUNDAY);
		System.out.println(foodTest);
		String mealType = foodTest.getMealType().name();
		System.out.println(mealType);
	}

}
