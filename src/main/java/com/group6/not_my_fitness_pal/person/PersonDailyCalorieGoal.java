package com.group6.not_my_fitness_pal.person;

import com.group6.not_my_fitness_pal.food.Day;

import java.util.Objects;

public class PersonDailyCalorieGoal {

    private Integer id;
    private String name;
    private Integer calorie_target;
    private Integer week;
    private Day day;
    private Integer total_calories_on_week_on_day;
    // These two are set in FoodService - Business Logic
    private Integer calorie_difference;
    private String calorie_goal_result;

    // GET RID OF IF CAUSING ISSUES
    public PersonDailyCalorieGoal(){};

    public PersonDailyCalorieGoal(Integer id, String name, Integer calorie_target, Integer week, Day day, Integer total_calories_on_week_on_day) {
        this.id = id;
        this.name = name;
        this.calorie_target = calorie_target;
        this.week = week;
        this.day = day;
        this.total_calories_on_week_on_day = total_calories_on_week_on_day;
        // NOTE: calorie_difference & calorie_goal_result will be set using the Setters!! - Set in Business Logic section (FoodService)
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalorie_target() {
        return calorie_target;
    }

    public void setCalorie_target(Integer calorie_target) {
        this.calorie_target = calorie_target;
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

    public Integer getTotal_calories_on_week_on_day() {
        return total_calories_on_week_on_day;
    }

    public void setTotal_calories_on_week_on_day(Integer total_calories_on_week_on_day) {
        this.total_calories_on_week_on_day = total_calories_on_week_on_day;
    }

    public Integer getCalorie_difference() {
        return calorie_difference;
    }

    public void setCalorie_difference(Integer calorie_difference) {
        this.calorie_difference = calorie_difference;
    }

    public String getCalorie_goal_result() {
        return calorie_goal_result;
    }

    public void setCalorie_goal_result(String calorie_goal_result) {
        this.calorie_goal_result = calorie_goal_result;
    }

    @Override
    public String toString() {
        return "PersonDailyCalorieGoal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calorie_target=" + calorie_target +
                ", week=" + week +
                ", day=" + day +
                ", total_calories_on_week_on_day=" + total_calories_on_week_on_day +
                ", calorie_difference=" + calorie_difference +
                ", calorie_goal_result='" + calorie_goal_result + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDailyCalorieGoal that = (PersonDailyCalorieGoal) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(calorie_target, that.calorie_target) && Objects.equals(week, that.week) && day == that.day && Objects.equals(total_calories_on_week_on_day, that.total_calories_on_week_on_day) && Objects.equals(calorie_difference, that.calorie_difference) && Objects.equals(calorie_goal_result, that.calorie_goal_result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, calorie_target, week, day, total_calories_on_week_on_day, calorie_difference, calorie_goal_result);
    }
}
