package com.group6.not_my_fitness_pal.person;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository("person_postgres")
public class PersonDataAccessService implements PersonDao{

    private JdbcTemplate jdbcTemplate;

    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getAllPeople() {
        String sql = """
        SELECT id, name, age, height_in_cm, weight_in_kg, calorie_target 
        FROM people
                """;
        RowMapper<Person> personRowMapper = (rs, rowNum) -> {
            return new Person(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getDouble("height_in_cm"),
                    rs.getDouble("weight_in_kg"),
                    rs.getInt("calorie_target")

                    );
        };
        List<Person> people = jdbcTemplate.query(sql, personRowMapper);
        return people;
    }


    @Override
    public Person getPersonById(Integer id) {
        //TODO: DO THIS FIRST - This is needed for FoodService
        String sql = """
                SELECT id, name, age, height_in_cm, weight_in_kg, calorie_target 
                FROM people WHERE id = ?
                """;

        RowMapper<Person> personRowMapper = (rs, rowNum) -> {
            return new Person(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getDouble("height_in_cm"),
                    rs.getDouble("weight_in_kg"),
                    rs.getInt("calorie_target")
            );
        };

        List<Person> people = jdbcTemplate.query(sql, personRowMapper, id);


        if (people.isEmpty()){
            return null;
        } else {
            return people.get(0);
        }

    }

    @Override
    public int addPerson(Person person) {
        return 0;
    }

    @Override
    public int deletePersonById(Integer id) {
        return 0;
    }

    @Override
    public int updatePersonById(Integer id, Person updatePerson) {
        return 0;
    }
}
