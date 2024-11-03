package si.uni.prpo.group03.venueservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import si.uni.prpo.group03.venueservice.model.Venue.VenueStatus;
import si.uni.prpo.group03.venueservice.model.Venue.VenueType;

public class ResponseVenueDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "Venue name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 2000)
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private List<String> amenities;

    @NotNull(message = "Status must be provided")
    private VenueStatus status;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal pricePerDay;

    private Timestamp openingTime;

    private Timestamp closingTime;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Contact email is required")
    private String contactEmail;

    @Size(max = 15, message = "Contact phone number should not exceed 15 characters")
    private String contactPhone;

    private List<String> photos;

    private VenueType venueType;

    @NotNull(message = "Owner ID must be provided")
    private Long ownerId;

    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Average rating cannot exceed 5")
    private Double averageRating;

    @Min(value = 0, message = "Rating count cannot be negative")
    private Integer ratingCount;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
