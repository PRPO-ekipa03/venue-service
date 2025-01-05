package si.uni.prpo.group03.venueservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import si.uni.prpo.group03.venueservice.model.Venue;
import java.sql.Timestamp;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Find venues by status (e.g., AVAILABLE or UNAVAILABLE)
    List<Venue> findByStatus(Venue.VenueStatus status);

    // Find venues by location (e.g., city or area)
    List<Venue> findByLocationContainingIgnoreCase(String location);

    // Optional: Find venues within a certain capacity range
    List<Venue> findByCapacityBetween(Integer minCapacity, Integer maxCapacity);

    // Optional: Find venues by type (e.g., CONFERENCE_HALL, MEETING_ROOM)
    List<Venue> findByVenueType(Venue.VenueType venueType);

    // Find venues by owner ID
    List<Venue> findByOwnerId(Long ownerId);

    // Custom query to find available venues (not reserved for a specific date)
    @Query("""
        SELECT v 
        FROM Venue v 
        WHERE v.location LIKE %:location%
        AND v.venueType = :venueType
        AND v.id NOT IN (
            SELECT r.venueId 
            FROM Reservation r 
            WHERE DATE(r.reservedDate) = DATE(:reservedDate)
                AND r.status = 'ACTIVE'
        )
    """)
    List<Venue> findAvailableVenues(
        @Param("location") String location,
        @Param("venueType") Venue.VenueType venueType,
        @Param("reservedDate") Timestamp reservedDate
    );
}
