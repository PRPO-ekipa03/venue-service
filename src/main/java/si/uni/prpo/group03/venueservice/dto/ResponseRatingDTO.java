package si.uni.prpo.group03.venueservice.dto;

import jakarta.validation.constraints.*;

public class ResponseRatingDTO {
    @NotNull(message = "Id must be sent")
    private Long ratingId;
    private Long venueId;
    @NotNull(message = "Rating must be given")
    private Double ratingValue;
    @NotNull(message = "New average must be given")
    private double newAverageRating;
    @NotNull(message = "New rating count must be given")
    private int newRatingCount;
    private String comment; // Add comment here
    

    // Getters and Setters
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public double getNewAverageRating() {
        return newAverageRating;
    }

    public void setNewAverageRating(double newAverageRating) {
        this.newAverageRating = newAverageRating;
    }

    public int getNewRatingCount() {
        return newRatingCount;
    }

    public void setNewRatingCount(int newRatingCount) {
        this.newRatingCount = newRatingCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
