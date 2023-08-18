package ru.rasim.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor

public class Booking {

    private Integer id;

    private final Person person;

    private final Book book;

    private final Date timeOfBooking;

}
