package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.sql.Timestamp;
import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;

@Schema(description = "Response Data Transfer Object for reservation details.")
public class ResponseReservationDTO {

    @NotNull
    @Schema(description = "Unique identifier of the reservation", example = "1")
    private Long id;  // Reservation ID

    @NotNull
    @Schema(description = "ID of the venue being reserved", example = "101")
    private Long venueId;  // ID of the venue being reserved

    @NotNull
    @Schema(description = "ID of the user who made the reservation", example = "42")
    private Long userId;

    @NotNull
    @Schema(description = "ID of the event associated with the reservation", example = "202")
    private Long eventId;  // ID of the event for which the reservation is made

    @NotNull
    @Schema(description = "Date and time of the reservation", example = "2023-08-15T14:30:00Z")
    private Timestamp reservedDate;  // Date of the reservation

    @Schema(description = "Current status of the reservation", example = "ACTIVE", allowableValues = {"ACTIVE", "PENDING", "CANCELED"})
    private ReservationStatus status;  // Status of the reservation (ACTIVE, PENDING, or CANCELED)

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
