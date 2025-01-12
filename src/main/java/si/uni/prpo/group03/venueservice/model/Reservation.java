package si.uni.prpo.group03.venueservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@Schema(description = "Representation of a reservation for a venue or event.")
@Entity
@Table(name = "reservations", indexes = {
    @Index(name = "idx_venue_date", columnList = "venue_id, reservedDate"),
    @Index(name = "idx_event", columnList = "event_id")
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the reservation", example = "1")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "UserId must be given")
    @Schema(description = "Identifier of the user who made the reservation", example = "42")
    private Long userId;

    @Column(name = "venue_id", nullable = false)
    @Schema(description = "Identifier of the venue being reserved", example = "101")
    private Long venueId;  

    @Column(name = "event_id", nullable = false)
    @NotNull(message = "EventId must be given")
    @Schema(description = "Identifier of the event associated with the reservation", example = "202")
    private Long eventId;  

    @Column(nullable = false)
    @Schema(description = "Timestamp of when the reservation was made", example = "2023-08-15T14:30:00Z")
    private Timestamp reservedDate;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the reservation", 
            allowableValues = {"ACTIVE", "PENDING", "CANCELED"}, 
            defaultValue = "PENDING")
    private ReservationStatus status = ReservationStatus.PENDING;

    @Schema(description = "Possible statuses of a reservation")
    public enum ReservationStatus {
        ACTIVE,
        PENDING,
        CANCELED
    }

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
