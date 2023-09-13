package ru.rasim.repositories.impl;

import jakarta.annotation.PreDestroy;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rasim.models.Book;
import ru.rasim.repositories.BooksRepository;

import java.util.List;

@Component
@Scope("singleton")
public class BooksRepositoryImpl extends AbstractCrudRepositoryImpl implements BooksRepository {

    private static final Class<Book> OBJECT_CLASS = Book.class;

    private static final String TABLE_NAME = "Book";

    private final BookingsRepositoryImpl bookingsRepository;

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

    @Autowired
    public BooksRepositoryImpl(BookingsRepositoryImpl bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @PreDestroy
    public void closeSession() {
        sessionFactory.close();
        System.out.printf("\"%s\" table has been closed\n", TABLE_NAME);
    }

    @Override
    public List<Book> showAll() {
        return inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.createQuery(String.format("SELECT o FROM %s o", TABLE_NAME), OBJECT_CLASS).list());
    }

    @Override
    public Long save(Book book) {
        inTransaction(sessionFactory.getCurrentSession(), session -> session.save(book));

        return book.getId();
    }

    @Override
    public Book show(Long id) {
       return inTransactionWithResult(sessionFactory.getCurrentSession(), session -> session.get(OBJECT_CLASS, id));
    }

    @Override
    public boolean update(Long id, Book updatedBook) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            updatedBook.setId(id);
            session.merge(updatedBook);
        });

        return true;
    }

    @Override
    public boolean delete(Long id) {
        inTransaction(sessionFactory.getCurrentSession(), session -> {
            Book book = session.get(OBJECT_CLASS, id);
            session.remove(book);
        });

        return true;
    }

     public Integer getNumberOfFreeBooks(Long bookId) {
        int totalNumberOfBooks = inTransactionWithResult(sessionFactory.getCurrentSession(),
                session -> session.get(OBJECT_CLASS, bookId)).getCount();

        int numberOfTakenBooks = bookingsRepository.showByBookId(bookId).size();

        return totalNumberOfBooks - numberOfTakenBooks;

    }

    public boolean isFreeBookExisting(Long bookId) {
        return getNumberOfFreeBooks(bookId) > 0;
    }

}
