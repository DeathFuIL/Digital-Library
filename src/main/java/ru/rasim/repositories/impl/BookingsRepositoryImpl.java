package ru.rasim.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.rasim.models.Booking;
import ru.rasim.repositories.BookingsRepository;

import java.sql.Date;
import java.util.List;


@Component
@Scope("singleton")
public class BookingsRepositoryImpl implements BookingsRepository {

    private static final String SQL_INSERT = "INSERT INTO Booking(book_id, person_id, start_time_of_booking, is_finished) VALUES(?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT * FROM Booking";

    private static final String SQL_SELECT_ALL_ACTIVE = "SELECT * FROM Booking WHERE is_finished = false";

    private static final String SQL_SELECT_ALL_FINISHED = "SELECT * FROM Booking WHERE is_finished = true";

    private static final String SQL_SELECT = "SELECT * FROM Booking WHERE id = ?";

    private static final String SQL_SELECT_BY_PERSON_ID = "SELECT * FROM Booking WHERE person_id = ? AND is_finished = false";

    private static final String SQL_UPDATE = "UPDATE Booking SET book_id = ?, person_id = ?, start_time_of_booking = ?, finish_time_of_booking = ?, is_finished = ? WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM Booking WHERE id = ?";


    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Booking> toBooking = (row, column) -> Booking.builder()
            .id(row.getInt("id"))
            .bookId(row.getInt("book_id"))
            .personId(row.getInt("person_id"))
            .startTimeOfBooking(row.getDate("start_time_of_booking"))
            .finishTimeOfBooking(row.getDate("finish_time_of_booking"))
            .isFinished(row.getBoolean("is_finished"))
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
        int result = jdbcTemplate.update(SQL_INSERT, booking.getBookId(), booking.getPersonId(), new Date(System.currentTimeMillis()), false);

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
        int result = jdbcTemplate.update(SQL_UPDATE, updatedBooking.getBookId(), updatedBooking.getPersonId(), updatedBooking.getStartTimeOfBooking(), updatedBooking.getFinishTimeOfBooking(), updatedBooking.isFinished() ,updatedBooking.getId());

        return result == 1;
    }

    @Override
    public boolean delete(Integer id) {
        int result = jdbcTemplate.update(SQL_DELETE, id);

        return result == 1;
    }

    public boolean finish(Integer id) {
        Booking booking = show(id);
        booking.setFinished(true);
        booking.setFinishTimeOfBooking(new Date(System.currentTimeMillis()));
        boolean result = update(id, booking);

        return result;
    }

    public List<Booking> showAllFinished() {
        return jdbcTemplate.query(SQL_SELECT_ALL_FINISHED, toBooking);
    }

    public List<Booking> showAllActive() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ACTIVE, toBooking);
    }

}
