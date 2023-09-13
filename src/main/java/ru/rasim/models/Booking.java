package ru.rasim.models;

import jakarta.persistence.*;
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
@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Min(value = 1, message = "Please, select person")
    @Column(name = "person_id")
    private Integer personId;

    @Min(value = 1, message = "Please, select book")
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "start_time_of_booking")
    private Date startTimeOfBooking;

    @Column(name = "finish_time_of_booking")
    private Date finishTimeOfBooking;

    @Column(name = "is_finished")
    private boolean isFinished;
}
