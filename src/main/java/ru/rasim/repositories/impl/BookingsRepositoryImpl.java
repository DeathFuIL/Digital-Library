package ru.rasim.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.rasim.models.Booking;
import ru.rasim.repositories.BookingsRepository;

import java.util.List;


@Component
@Scope("singleton")
public class BookingsRepositoryImpl implements BookingsRepository {

    private static final String SQL_INSERT = "INSERT INTO Booking(book_id, person_id, time_of_booking) VALUES(?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT * FROM Booking";

    private static final String SQL_SELECT = "SELECT * FROM Booking WHERE id = ?";

    private static final String SQL_SELECT_BY_PERSON_ID = "SELECT * FROM Booking WHERE person_id = ?";

    private static final String SQL_UPDATE = "UPDATE Booking SET book_id = ?, person_id = ?, time_of_booking = ? WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM Booking WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Booking> toBooking = (row, column) -> Booking.builder()
            .bookId(row.getInt("book_id"))
            .personId(row.getInt("person_id"))
            .timeOfBooking(row.getDate("time_of_booking"))
            .build();

    @Autowired
    public BookingsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Booking> showAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, toBooking);
    }

    @Override
    public boolean save(Booking booking) {
        int result = jdbcTemplate.update(SQL_INSERT, booking.getBookId(), booking.getPersonId(), booking.getTimeOfBooking());

        return result == 1;
    }

    @Override
    public Booking show(Integer id) {
        return jdbcTemplate.query(SQL_SELECT, toBooking, id).stream().findAny().orElse(null);
    }

    public List<Booking> showByPersonId(Integer id) {
        return jdbcTemplate.query(SQL_SELECT_BY_PERSON_ID, toBooking, id);
    }

    @Override
    public boolean update(Integer id, Booking updatedBooking) {
        int result = jdbcTemplate.update(SQL_UPDATE, updatedBooking.getBookId(), updatedBooking.getPersonId(), updatedBooking.getTimeOfBooking(), updatedBooking.getId());

        return result == 1;
    }

    @Override
    public boolean delete(Integer id) {
        int result = jdbcTemplate.update(SQL_DELETE, id);

        return result == 1;
    }
}
