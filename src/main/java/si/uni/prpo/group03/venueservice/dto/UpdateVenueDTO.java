package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import si.uni.prpo.group03.venueservice.model.Venue.VenueStatus;
import si.uni.prpo.group03.venueservice.model.Venue.VenueType;

@Schema(description = "Data Transfer Object for updating venue details.")
public class UpdateVenueDTO {

    @Size(max = 100, message = "Venue name should not exceed 100 characters")
    @Schema(description = "Name of the venue", maxLength = 100, example = "Grand Hall")
    private String name;

    @Size(max = 2000, message = "Description should not exceed 2000 characters")
    @Schema(description = "Description of the venue", maxLength = 2000, example = "A spacious hall suitable for conferences and events.")
    private String description;

    @Schema(description = "Location of the venue", example = "Ljubljana")
    private String location;

    @Schema(description = "Address of the venue", example = "Preš 10, Ljubljana")
    private String address;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Schema(description = "Capacity of the venue", example = "300")
    private Integer capacity;

    @Schema(description = "List of amenities available at the venue", example = "[\"WiFi\", \"Projector\", \"Sound System\"]")
    private List<String> amenities;

    @Schema(description = "Current status of the venue", example = "AVAILABLE")
    private VenueStatus status;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Price per day for renting the venue", example = "150.00")
    private BigDecimal pricePerDay;

    @Schema(description = "Opening time of the venue", example = "2023-08-15T08:00:00Z")
    private Timestamp openingTime;

    @Schema(description = "Closing time of the venue", example = "2023-08-15T22:00:00Z")
    private Timestamp closingTime;

    @Email(message = "Invalid email format")
    @Schema(description = "Contact email for the venue", example = "contact@venue.com")
    private String contactEmail;

    @Size(max = 15, message = "Contact phone number should not exceed 15 characters")
    @Schema(description = "Contact phone number for the venue", maxLength = 15, example = "+38640123456")
    private String contactPhone;

    @Schema(description = "List of photo URLs for the venue", example = "[\"http://example.com/photo1.jpg\", \"http://example.com/photo2.jpg\"]")
    private List<String> photos;

    @Schema(description = "Type/category of the venue", example = "CONFERENCE_HALL")
    private VenueType venueType;

    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Average rating cannot exceed 5")
    @Schema(description = "Average rating of the venue", example = "4.5")
    private Double averageRating;

    @Min(value = 0, message = "Rating count cannot be negative")
    @Schema(description = "Total number of ratings for the venue", example = "10")
    private Integer ratingCount;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public VenueStatus getStatus() {
        return status;
    }

    public void setStatus(VenueStatus status) {
        this.status = status;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Timestamp getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Timestamp openingTime) {
        this.openingTime = openingTime;
    }

    public Timestamp getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Timestamp closingTime) {
        this.closingTime = closingTime;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public VenueType getVenueType() {
        return venueType;
    }

    public void setVenueType(VenueType venueType) {
        this.venueType = venueType;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}
