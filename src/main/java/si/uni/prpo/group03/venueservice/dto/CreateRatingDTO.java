package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Data Transfer Object for creating a new rating.")
public class CreateRatingDTO {
    
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    @NotNull(message = "Rating must be given")
    @Schema(description = "Value of the rating", example = "8.5")
    private Double ratingValue;

    @Schema(description = "Optional comment provided by the user", example = "Great venue with excellent facilities.")
    private String comment;

    // Getters and Setters
    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
