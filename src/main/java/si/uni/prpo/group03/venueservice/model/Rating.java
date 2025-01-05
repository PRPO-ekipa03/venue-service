package si.uni.prpo.group03.venueservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    @NotNull(message = "Rating must be given")
    private Double ratingValue;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY helps optimize performance
    @JoinColumn(name = "venue_id", nullable = false) // Foreign key column to reference Venue
    private Venue venue;

    @NotNull(message = "userId must be given")
    private Long userId;

    private String comment;

    private Timestamp timestamp;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getRatingValue() {
        return ratingValue;
    }
    
    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }
    
    public Venue getVenue() {
        return venue;
    }
    
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}

