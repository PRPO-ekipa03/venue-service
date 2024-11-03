package si.uni.prpo.group03.venueservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "reservations", indexes = {
    @Index(name = "idx_venue_date", columnList = "venue_id, reservedDate"),
    @Index(name = "idx_event", columnList = "event_id")
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venue_id", nullable = false)
    private Long venueId;  

    @Column(name = "event_id", nullable = false)
    @NotNull(message = "EventId must be given")
    private Long eventId;  

    @Column(nullable = false)
    private Timestamp reservedDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING; // Default status as active

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

