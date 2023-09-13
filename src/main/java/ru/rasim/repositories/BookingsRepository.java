package ru.rasim.repositories;

import ru.rasim.models.Booking;

import java.util.List;

public interface BookingsRepository extends CrudRepository<Booking> {
    boolean finish(Long id);

    List<Booking> showAllActive();

    List<Booking> showAllFinished();

    List<Booking> showByPersonId(Long id);
}
