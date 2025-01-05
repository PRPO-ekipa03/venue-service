package si.uni.prpo.group03.venueservice.dto;

import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;

import java.sql.Timestamp;

public class UpdateReservationDTO {

    private Timestamp reservedDate;

    private ReservationStatus status;

    private Long venueId;

    // Getters and Setters

    public Timestamp getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Timestamp reservedDate) {
        this.reservedDate = reservedDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}

