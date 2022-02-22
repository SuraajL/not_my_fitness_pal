package com.group6.not_my_fitness_pal.person;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person {
    private Integer id;
    private String name;
    private Integer age;
//    private LocalDateTime start_date;
    private Double height_in_cm;
    private Double weight_in_kg;
    private Integer calorie_target;

    // GET RID OF IF CAUSING ISSUES
    public Person(){};

    public Person(Integer id, String name, Integer age, Double height_in_cm, Double weight_in_kg, Integer calorie_target) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height_in_cm = height_in_cm;   // Optional field - we will allow a null value when applying logic
        this.weight_in_kg = weight_in_kg;   // Optional field - we will allow a null value when applying logic
        this.calorie_target = calorie_target;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight_in_cm() {
        return height_in_cm;
    }

    public void setHeight_in_cm(Double height_in_cm) {
        this.height_in_cm = height_in_cm;
    }

    public Double getWeight_in_kg() {
        return weight_in_kg;
    }

    public void setWeight_in_kg(Double weight_in_kg) {
        this.weight_in_kg = weight_in_kg;
    }

    public Integer getCalorie_target() {
        return calorie_target;
    }

    public void setCalorie_target(Integer calorie_target) {
        this.calorie_target = calorie_target;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height_in_cm=" + height_in_cm +
                ", weight_in_kg=" + weight_in_kg +
                ", calorie_target=" + calorie_target +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(height_in_cm, person.height_in_cm) && Objects.equals(weight_in_kg, person.weight_in_kg) && Objects.equals(calorie_target, person.calorie_target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, height_in_cm, weight_in_kg, calorie_target);
    }
}
