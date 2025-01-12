package si.uni.prpo.group03.venueservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@Schema(description = "Representation of a rating given by a user for a venue.")
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the rating", example = "1")
    private Long id;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    @NotNull(message = "Rating must be given")
    @Schema(description = "Value of the rating", example = "8.5")
    private Double ratingValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    @Schema(description = "Venue associated with the rating")
    private Venue venue;

    @NotNull(message = "userId must be given")
    @Schema(description = "Identifier of the user who gave the rating", example = "42")
    private Long userId;

    @NotNull(message = "user fullName must be given")
    @Schema(description = "Full name of the user who provided the rating", example = "John Doe")
    private String fullName;

    @NotNull(message = "")
    @Schema(description = "Comment provided by the user", example = "Great venue with excellent service.")
    private String comment;

    @Schema(description = "Timestamp when the rating was made", example = "2023-08-15T14:30:00Z")
    private Timestamp timestamp;

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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
