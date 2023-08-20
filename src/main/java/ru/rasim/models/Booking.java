package ru.rasim.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Booking {

    private Integer id;

    @NotNull(message = "Please, select person")
    private Integer personId;

    @NotNull(message = "Please, select book")
    private Integer bookId;

    private Date startTimeOfBooking;

    private Date finishTimeOfBooking;


    private boolean isFinished;
}
