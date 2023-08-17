package ru.rasim.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rasim.models.Person;
import ru.rasim.repositories.PersonsRepository;

import java.util.List;


@Component
@Scope("singleton")
public class PersonsRepositoryImpl implements PersonsRepository {
    
    private static final String TABLE_NAME = "Person";
    
    private static final String SQL_INSERT = "INSERT INTO Person(name, surname, age, email) VALUES(?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT * FROM Person";

    private static final String SQL_SELECT = "SELECT * FROM Person WHERE id = ?";

    private static final String SQL_UPDATE = "UPDATE Person SET name = ?, surname = ?, age = ?, email = ? WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM Person WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> showAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public boolean save(Person person) {
        int result = jdbcTemplate.update(SQL_INSERT, person.getName(), person.getSurname(), person.getAge(), person.getEmail());

        return result == 1;
    }

    @Override
    public Person show(Integer id) {
        return jdbcTemplate.query(SQL_SELECT, new BeanPropertyRowMapper<>(Person.class), id).stream()
                .findAny().orElse(null);
    }

    @Override
    public boolean update(Integer id, Person updatedPerson) {
        int result =  jdbcTemplate.update(SQL_UPDATE, updatedPerson.getName(), updatedPerson.getSurname(),
                updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getId() );

        return result == 1;
    }

    @Override
    public boolean delete(Integer id) {
        int result = jdbcTemplate.update(SQL_DELETE, id);

        return result == 1;
    }

}
