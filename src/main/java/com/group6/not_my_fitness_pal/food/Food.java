package com.group6.not_my_fitness_pal.food;

import java.util.Objects;

public class Food {

    // Integers - so can handle nulls
    private Integer id;
    private Integer person_id;
    private String name;
    private MealType mealType;
    private String notes;
    private Integer calories;
    private Integer week;
    private Day day;

    // GET RID OF IF CAUSING ISSUES
    public Food(){};

    public Food(Integer id, Integer person_id, String name, MealType mealType, String notes, Integer calories, Integer week, Day day) {
        this.id = id;
        this.person_id = person_id;
        this.name = name;
        this.mealType = mealType;
        this.notes = notes;
        this.calories = calories;
        this.week = week;
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", person_id=" + person_id +
                ", name='" + name + '\'' +
                ", mealType=" + mealType +
                ", notes='" + notes + '\'' +
                ", calories=" + calories +
                ", week=" + week +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id) && Objects.equals(person_id, food.person_id) && Objects.equals(name, food.name) && mealType == food.mealType && Objects.equals(notes, food.notes) && Objects.equals(calories, food.calories) && Objects.equals(week, food.week) && day == food.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person_id, name, mealType, notes, calories, week, day);
    }

}
