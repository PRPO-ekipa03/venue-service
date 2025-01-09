package si.uni.prpo.group03.venueservice.dto;

import java.sql.Timestamp;
import jakarta.validation.constraints.*;
import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;


public class ResponseReservationDTO {

    @NotNull
    private Long id;  // Reservation ID

    @NotNull
    private Long venueId;  // ID of the venue being reserved

    @NotNull
    private Long userId;

    @NotNull
    private Long eventId;  // ID of the event for which the reservation is made

    @NotNull
    private Timestamp reservedDate;  // Date of the reservation

    
    private ReservationStatus status;  // Status of the reservation (ACTIVE or CANCELED)

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

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
}
