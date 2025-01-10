package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;

import java.sql.Timestamp;

@Schema(description = "Data Transfer Object for updating reservation details.")
public class UpdateReservationDTO {

    @Schema(description = "The new date and time for the reservation", example = "2023-08-15T14:30:00Z")
    private Timestamp reservedDate;

    @Schema(description = "The new status of the reservation", example = "ACTIVE", allowableValues = {"ACTIVE", "PENDING", "CANCELED"})
    private ReservationStatus status;

    @Schema(description = "The ID of the venue associated with the reservation", example = "101")
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
