package ru.rasim.repositories.impl;

import jakarta.annotation.PreDestroy;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rasim.models.Person;
import ru.rasim.repositories.PersonsRepository;

import java.util.List;


@Component
@Scope("singleton")
public class PersonsRepositoryImpl extends CrudRepositoryImpl implements PersonsRepository {
    
    private static final Class<Person> OBJECT_CLASS = Person.class;
    
    private static final String TABLE_NAME = "Person";

    private final SessionFactory sessionFactory;

    {
        SessionFactory testSessionFactory = null;
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(OBJECT_CLASS);
            testSessionFactory = configuration.buildSessionFactory();

            System.out.printf("Connecting to \"%s\" table has been established\n", TABLE_NAME);
        } catch (HibernateException e) {
            throw new RuntimeException(String.format("Connecting to \"%s\" table is failed", TABLE_NAME));
        } finally {
            sessionFactory = testSessionFactory;
        }
    }

    @PreDestroy
    public void closeSession() {
        sessionFactory.close();
        System.out.printf("\"%s\" table has been closed\n", TABLE_NAME);
    }

    @Override
    public List<Person> showAll() {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(String.format("SELECT o FROM %s o", TABLE_NAME), OBJECT_CLASS).list());
    }

    @Override
    public Long save(Person person) {
        inTransaction(sessionFactory.getCurrentSession(), session -> session.save(person));

        return person.getId();
    }

    @Override
    public Person show(Long id) {
        return inTransactionWithResult(sessionFactory.getCurrentSession(), session -> session.get(OBJECT_CLASS, id));
    }

    @Override
    public boolean update(Long id, Person updatedPerson) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            updatedPerson.setId(id);
            session.merge(updatedPerson);
        });

        return true;
    }

    @Override
    public boolean delete(Long id) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            Person person = session.get(OBJECT_CLASS ,id);
            session.remove(person);
        });

        return true;
    }

}
