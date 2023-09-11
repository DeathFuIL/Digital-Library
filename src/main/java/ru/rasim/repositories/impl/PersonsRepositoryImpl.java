package ru.rasim.repositories.impl;

import jakarta.annotation.PreDestroy;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rasim.models.Person;
import ru.rasim.repositories.PersonsRepository;

import java.util.List;


@Component
@Scope("singleton")
public class PersonsRepositoryImpl implements PersonsRepository {
    
    private static final String TABLE_NAME = "Person";

    private final Session session;

    {
        Session testSession = null;
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            testSession = sessionFactory.getCurrentSession();
            System.out.println("Connecting to \"Person\" table has been established");
        } catch (HibernateException e) {
            throw new RuntimeException("Connecting to \"Person\" table is failed");
        } finally {
            session = testSession;
            session.beginTransaction();
        }
    }

    @PreDestroy
    public void closeSession() {
        session.close();
        System.out.println("\"Person\" table has been closed");
    }

    @Override
    public List<Person> showAll() {
        return session.createQuery(String.format("SELECT p FROM %s p", TABLE_NAME), Person.class).list();
    }

    @Override
    public Long save(Person person) {
        session.save(person);

        return person.getId();
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
