package ru.rasim.repositories.impl;

import jakarta.annotation.PreDestroy;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rasim.models.Booking;
import ru.rasim.repositories.BookingsRepository;

import java.sql.Date;
import java.util.List;


@Component
@Scope("singleton")
public class BookingsRepositoryImpl extends AbstractCrudRepositoryImpl implements BookingsRepository {
    
    private static final Class<Booking> OBJECT_CLASS = Booking.class;
    
    private static final String TABLE_NAME = "Booking";

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
    public List<Booking> showAll() {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(String.format("SELECT o from %s o", TABLE_NAME), OBJECT_CLASS).list());
    }

    @Override
    public Long save(Booking booking) {
        booking.setStartTimeOfBooking(new Date(System.currentTimeMillis()));

        inTransaction(sessionFactory.getCurrentSession(), session -> session.save(booking));

        return booking.getId();
    }

    @Override
    public Booking show(Long id) {
        return inTransactionWithResult(sessionFactory.getCurrentSession(), session -> session.get(OBJECT_CLASS, id));
    }

    @Override
    public List<Booking> showByPersonId(Long id) {
       return inTransactionWithResult(sessionFactory.getCurrentSession(),
               session -> session.createQuery(
                       String.format("SELECT o FROM %s o WHERE personId = %d AND isFinished = false", TABLE_NAME, id),
                       OBJECT_CLASS).list()
       );
    }

    public List<Booking> showByBookId(Long bookId) {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(
                        String.format("SELECT o FROM %s o WHERE bookId = %d AND isFinished = false", TABLE_NAME, bookId),
                        OBJECT_CLASS).list()
        );
    }

    @Override
    public boolean update(Long id, Booking updatedBooking) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            updatedBooking.setId(id);
            session.merge(updatedBooking);
        });

        return true;
    }

    @Override
    public boolean delete(Long id) {
        Booking booking = show(id);

        inTransaction(sessionFactory.getCurrentSession(), session -> {
            session.remove(booking);
        });

        return true;
    }

    public boolean finish(Long id) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            Booking booking = session.get(OBJECT_CLASS, id);
            booking.setFinished(true);
            booking.setFinishTimeOfBooking(new Date(System.currentTimeMillis()));
        });

        return true;
    }

    public List<Booking> showAllFinished() {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(
                        String.format("SELECT o FROM %s o WHERE isFinished = true", TABLE_NAME), OBJECT_CLASS).list()
        );
    }

    public List<Booking> showAllActive() {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(
                        String.format("SELECT o FROM %s o WHERE isFinished = false", TABLE_NAME), OBJECT_CLASS).list()
        );
    }
}
