package com.group6.not_my_fitness_pal.person;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("person_postgres")
public class PersonDataAccessService implements PersonDao{

    private JdbcTemplate jdbcTemplate;  // Make JDBC template a property so that we can use its methods

    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getAllPeople() {
        String sql = """
        SELECT id, name, age, height_in_cm, weight_in_kg, calorie_target 
        FROM people
                """;
        RowMapper<Person> personRowMapper = (rs, rowNum) -> {   // Mapping each sql row to Java code
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
            return people.get(0);   // Returns first element from people list we've just made (contains sql mappings)
            // - this first element is the person we've found by their id
        }
    }

    @Override
    public int addPerson(Person person) {
        String sql = """
                INSERT INTO people(name, age, height_in_cm, weight_in_kg, calorie_target) 
                VALUES(?,?,?,?,?)
                """;
        int rowsAffected = jdbcTemplate.update(sql,
                person.getName(),
                person.getAge(),
                person.getHeight_in_cm(),
                person.getWeight_in_kg(),
                person.getCalorie_target());
        return rowsAffected;
    }

    @Override
    public int deletePersonById(Integer id) {
        String sql = "DELETE FROM people WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected;
    }

    @Override
    public int updatePersonById(Integer id, Person updatePerson) {
        String sql = """
                UPDATE people SET (name, age, height_in_cm, weight_in_kg, calorie_target) = (?, ?, ?, ?, ?)
                WHERE id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                updatePerson.getName(),
                updatePerson.getAge(),
                updatePerson.getHeight_in_cm(),
                updatePerson.getWeight_in_kg(),
                updatePerson.getCalorie_target(),
                id
        );
        return rowsAffected;
    }
}
