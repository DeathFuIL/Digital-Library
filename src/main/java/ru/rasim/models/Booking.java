package ru.rasim.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Booking {

    private Integer id;

    @Min(value = 1, message = "Please, select person")
    private Integer personId;

    @Min(value = 1, message = "Please, select book")
    private Integer bookId;

    private Date startTimeOfBooking;

    private Date finishTimeOfBooking;

    private boolean isFinished;
}
