package si.uni.prpo.group03.venueservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import si.uni.prpo.group03.venueservice.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Custom query method to find ratings by venue ID
    List<Rating> findByVenueId(Long venueId);

    // Paginated query for ratings by venue ID
    Page<Rating> findByVenueId(Long venueId, Pageable pageable);

    // Custom query method to find ratings by user ID, if you want to track user ratings
    List<Rating> findByUserId(Long userId);
}