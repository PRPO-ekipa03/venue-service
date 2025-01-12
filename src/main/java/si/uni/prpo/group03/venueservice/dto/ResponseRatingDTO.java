package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Response Data Transfer Object for rating details.")
public class ResponseRatingDTO {

    @NotNull(message = "Id must be sent")
    @Schema(description = "Unique identifier of the rating", example = "1")
    private Long ratingId;

    @Schema(description = "Identifier of the venue being rated", example = "101")
    private Long venueId;

    @NotNull(message = "Rating must be given")
    @Schema(description = "Value of the rating", example = "8.5")
    private Double ratingValue;

    @NotNull(message = "New average must be given")
    @Schema(description = "New average rating of the venue after this rating", example = "4.6")
    private double newAverageRating;

    @NotNull(message = "New rating count must be given")
    @Schema(description = "New total number of ratings for the venue", example = "15")
    private int newRatingCount;

    @NotNull(message = "User fullname must be given")
    @Schema(description = "Full name of the user who provided the rating", example = "John Doe")
    private String fullName;

    @Schema(description = "Optional comment provided by the user", example = "Great venue with excellent facilities.")
    private String comment; // Add comment here

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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
