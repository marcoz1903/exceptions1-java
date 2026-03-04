package model.entities;

import model.entities.exceptions.ReservationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Reservation {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer roomNumber;
    private LocalDate checkin;
    private LocalDate checkout;

    public Reservation() {
    }

    public Reservation(Integer roomNumber, LocalDate checkin, LocalDate checkout) {
        validateDates(checkin, checkout);
        this.roomNumber = roomNumber;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public Long duration() {
        return ChronoUnit.DAYS.between(checkin,checkout); //mais seguro que Period.
    }

    public String updateDates(LocalDate checkin, LocalDate checkout) {
        validateDates(checkin, checkout);
        this.checkin = checkin;
        this.checkout = checkout;
        return null;
    }

    public static void validateDates(LocalDate checkin, LocalDate checkout) {
        LocalDate now = LocalDate.now();
        if (checkin.isBefore(now) || checkout.isBefore(now)) {
            throw new ReservationException("Reservation dates for update must be future dates");
        }
        if (!checkout.isAfter(checkin)) {
            throw new ReservationException("Error in reservation: Check-out date must be after check-in date");
        }
    }

    @Override
    public String toString() {
        return  "Room "
                +roomNumber
                +", "
                +"check-in: "
                +checkin.format(fmt)//Mostrando objeto DateTime no formato String.
                +", "
                +"check-out: "
                +checkout.format(fmt)
                +", "
                +duration()
                +" nights";

    }

