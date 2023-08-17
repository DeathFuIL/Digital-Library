package ru.rasim.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rasim.models.Book;
import ru.rasim.repositories.BooksRepository;

import java.util.List;

@Component
@Scope("singleton")
public class BooksRepositoryImpl implements BooksRepository {

    private static final String SQL_INSERT = "INSERT INTO Book(name, author, year) VALUES(?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT * FROM Book";

    private static final String SQL_SELECT = "SELECT * FROM Book WHERE id = ?";

    private static final String SQL_UPDATE = "UPDATE Book SET name = ?, author = ?, year = ? WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM Book WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> showAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public boolean save(Book book) {
        int result = jdbcTemplate.update(SQL_INSERT, book.getName(), book.getAuthor(), book.getYear());

        return result == 1;
    }

    @Override
    public Book show(Integer id) {
        return jdbcTemplate.query(SQL_SELECT, new BeanPropertyRowMapper<>(Book.class), id).stream()
                .findAny().orElse(null);
    }

    @Override
    public boolean update(Integer id, Book updatedBook) {
        int result = jdbcTemplate.update(SQL_UPDATE, updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear());

        return result == 1;
    }

    @Override
    public boolean delete(Integer id) {
        int result = jdbcTemplate.update(SQL_DELETE, id);

        return result == 1;
    }

}
