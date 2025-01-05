package si.uni.prpo.group03.venueservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import si.uni.prpo.group03.venueservice.model.Reservation;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Find reservations by venue ID
    List<Reservation> findByVenueId(Long venueId);

    // Find reservations by event ID
    List<Reservation> findByEventId(Long eventId);

    // Find reservations within a date range for a specific venue
    List<Reservation> findByVenueIdAndReservedDateBetween(Long venueId, Timestamp startDate, Timestamp endDate);

    // Find reservations by status (e.g., ACTIVE or CANCELED)
    List<Reservation> findByStatus(Reservation.ReservationStatus status);

    // Custom query: Find active reservations by venue and date, ordered by date
    @Query("SELECT r FROM Reservation r WHERE r.venueId = :venueId AND r.status = 'ACTIVE' ORDER BY r.reservedDate ASC")
    List<Reservation> findActiveReservationsByVenueOrderedByDate(@Param("venueId") Long venueId);
}
