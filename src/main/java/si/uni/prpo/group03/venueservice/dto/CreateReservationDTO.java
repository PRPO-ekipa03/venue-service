package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;

@Schema(description = "Data Transfer Object for creating a new reservation.")
public class CreateReservationDTO {

    @NotNull(message = "Venue ID is required")
    @Schema(description = "Identifier of the venue being reserved", example = "101")
    private Long venueId;

    @NotNull(message = "Event ID is required")
    @Schema(description = "Identifier of the event for which the reservation is made", example = "202")
    private Long eventId;

    @NotNull(message = "User ID is required")
    @Schema(description = "Identifier of the user who is making the reservation", example = "42")
    private Long userId;

    @NotNull(message = "Reservation date is required")
    @Schema(description = "Date and time of the reservation", example = "2023-08-15T14:30:00Z")
    private Timestamp reservedDate;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the reservation", 
            example = "PENDING", 
            allowableValues = {"ACTIVE", "PENDING", "CANCELED"}, 
            defaultValue = "PENDING")
    private ReservationStatus status = ReservationStatus.PENDING; // Default status as active

    // Getters and Setters

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