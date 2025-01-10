package si.uni.prpo.group03.venueservice.specifications;

import si.uni.prpo.group03.venueservice.model.Venue;
import si.uni.prpo.group03.venueservice.model.Reservation;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class VenueSpecifications {

    // Private constructor to prevent instantiation
    private VenueSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Specification<Venue> findAvailableVenues(
            String location,
            Venue.VenueType venueType,
            Timestamp reservedDate
    ) {
        return (Root<Venue> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (location != null) {
                predicates.add(
                    builder.like(root.get("location"), "%" + location + "%")
                );
            }

            if (venueType != null) {
                predicates.add(
                    builder.equal(root.get("venueType"), venueType)
                );
            }

            if (reservedDate != null) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Reservation> reservationRoot = subquery.from(Reservation.class);
                subquery.select(reservationRoot.get("venueId"));

                Predicate dateMatch = builder.equal(
                    builder.function("DATE", java.sql.Date.class, reservationRoot.get("reservedDate")),
                    builder.function("DATE", java.sql.Date.class, builder.literal(reservedDate))
                );
                Predicate statusActive = builder.equal(reservationRoot.get("status"), "ACTIVE");

                subquery.where(builder.and(dateMatch, statusActive));
                predicates.add(builder.not(root.get("id").in(subquery)));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
