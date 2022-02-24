package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.Person;
import com.group6.not_my_fitness_pal.person.PersonDailyCalorieGoal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("food_postgres")
public class FoodDataAccessService implements FoodDao {

    private JdbcTemplate jdbcTemplate; // Make JDBC template a property so that we can use its methods

    public FoodDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addFood(Food food) {
        // Note the sql command doesn't need the id as it is a SERIAL and is generated automatically
        String sql = """
                INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                """;

        // Use .update Method when Inserting/deleting/updating
        int rowsAffected = jdbcTemplate.update(
                sql,
                food.getPerson_id(),
                food.getName(),
                food.getMealType().name(),
                food.getNotes(),
                food.getCalories(),
                food.getWeek(),
                food.getDay().name()
        );
        // Should return 1 as only 1 row is added - anything else it should throw error in the service!!
        return rowsAffected;
    }


    @Override
    public int deleteFoodById(Integer id) {
        String sql = "DELETE FROM food_entries WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        return result;

    }

    @Override
    public int updateFoodById(Integer id, Food updateFood) {
        String sql = """
                UPDATE food_entries SET (person_id, name, meal_type, notes, calories, week, day) = (?, ?, ?, ?, ?, ?, ?)
                WHERE id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                updateFood.getPerson_id(),
                updateFood.getName(),
                updateFood.getMealType().name(),
                updateFood.getNotes(),
                updateFood.getCalories(),
                updateFood.getWeek(),
                updateFood.getDay().name(),
                id
        );

        return rowsAffected;
    }

    @Override
    public Food getFoodById(Integer id) {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day
                FROM food_entries WHERE id = ?
                """;

        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {
            return new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))
            );
        };

        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, id);


        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList.get(0);  // Returns first element from food list we've just made (contains sql mappings)
            // - this first element is the food entry we've found by their id
        }
    }


    //more of a developer need?
    @Override
    public List<Food> getAllFood() {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day 
                FROM food_entries
                """;
        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Food food = new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")), //converts meal_type input to an enum
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))  //converts day input to a Day

            );
            return food; //so its not lost in the heap
        };
        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper);
        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList;
        }
    }


    @Override
    public List<Food> getFoodEntriesByPersonId(Integer person_id) {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day 
                FROM food_entries WHERE person_id = ?
                """;
        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Food food = new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))

            );
            return food;
        };
        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, person_id);
        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList;
        }
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeek(Integer person_id, Integer week) {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day 
                FROM food_entries WHERE person_id = ? AND week = ?
                """;
        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Food food = new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))

            );
            return food;
        };
        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, person_id, week);
        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList;
        }
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeekByDay(Integer person_id, Integer week, Day day) {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day 
                FROM food_entries WHERE person_id = ? AND week = ? AND day = ?
                """;                                                        //should we put quotations around ?
        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Food food = new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))

            );
            return food;
        };
        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, person_id, week, day.name()); //convert enum to string when passing sql query
        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList;
        }
    }


    @Override
    public List<Food> getFoodEntriesByMealType(MealType mealType) {
        String sql = """
                SELECT id, person_id, name, meal_type, notes, calories, week, day 
                FROM food_entries WHERE meal_type = ?
                """;
        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Food food = new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))

            );
            return food;
        };
        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, mealType.name());
        if (foodList.isEmpty()) {
            return null;
        } else {
            return foodList;
        }
    }

    @Override
    public List<PersonDailyCalorieGoal> getDailyCalorieGoalsByWeekByDay(Integer week, Day day) {
        String sql = """
                SELECT food_entries.person_id, people.name, people.calorie_target, SUM(food_entries.calories) AS total_calorie_intake, food_entries.day 
                FROM food_entries 
                INNER JOIN people
                ON food_entries.person_id = people.id
                WHERE food_entries.day = ? AND food_entries.week = ?
                GROUP BY (person_id, people.name, people.calorie_target, day)
                """;

        RowMapper<PersonDailyCalorieGoal> personDailyCalorieGoalRowMapper = (rs, rowNum) -> {
            return new PersonDailyCalorieGoal(
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    rs.getInt("calorie_target"),
                    week,
                    Day.valueOf(rs.getString("day")),
                    rs.getInt("total_calorie_intake")
            );
        };

        List<PersonDailyCalorieGoal> personDailyCalorieGoalList = jdbcTemplate.query(sql, personDailyCalorieGoalRowMapper, day.name(), week);
        if (personDailyCalorieGoalList.isEmpty()) {
            return null;
        } else {
            return personDailyCalorieGoalList;
        }

    }
}

