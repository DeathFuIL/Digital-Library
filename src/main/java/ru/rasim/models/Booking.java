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

    private final Integer personId;

    private final Integer bookId;

    private final Date timeOfBooking;

}
