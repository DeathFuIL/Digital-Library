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
    public Person show(Long id) {
        return session.get(Person.class, id);
    }

    @Override
    public boolean update(Long id, Person updatedPerson) {
        Person originPerson = show(id);

        originPerson.setName(updatedPerson.getName());
        originPerson.setSurname(updatedPerson.getSurname());
        originPerson.setAge(updatedPerson.getAge());
        originPerson.setEmail(updatedPerson.getEmail());

        return true;
    }

    @Override
    public boolean delete(Long id) {
        Person person = show(id);
        session.remove(person);

        return true;
    }

}
