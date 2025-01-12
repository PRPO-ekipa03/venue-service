package si.uni.prpo.group03.venueservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "venues", indexes = {
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_venue_type", columnList = "venueType"),
    @Index(name = "idx_status", columnList = "status")
})
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the venue", example = "1")
    private Long id;

    @Version
    @Column(name="OPTLOCK")
    @Schema(description = "Version for optimistic locking", example = "1")
    private Integer version;

    @Column(nullable = false)
    @NotBlank(message = "Venue name is required")
    @Size(max = 100)
    @Schema(description = "Name of the venue", maxLength = 100, example = "Grand Hall")
    private String name;

    @Column(nullable = true)
    @Size(max = 2000)
    @Schema(description = "Description of the venue", maxLength = 2000, example = "A spacious hall for events.")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Location is required")
    @Schema(description = "Broader location of the venue (e.g., city)", example = "Ljubljana")
    private String location;

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
    @Schema(description = "Physical address of the venue", example = "Preš 10")
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Schema(description = "Maximum capacity of the venue", example = "300")
    private Integer capacity;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "venue_amenities", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "amenity")
    @Schema(description = "List of amenities available at the venue")
    private List<String> amenities;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status must be provided")
    @Schema(description = "Current status of the venue", example = "AVAILABLE")
    private VenueStatus status;

    @Column(precision = 5, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Price per day for renting the venue", example = "150.00")
    private BigDecimal pricePerDay;

    @Schema(description = "Opening time of the venue", example = "08:00:00")
    private Timestamp openingTime;

    @Schema(description = "Closing time of the venue", example = "22:00:00")
    private Timestamp closingTime;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Contact email is required")
    @Schema(description = "Contact email for the venue", example = "contact@venue.com")
    private String contactEmail;

    @Size(max = 15, message = "Contact phone number should not exceed 15 characters")
    @Schema(description = "Contact phone number for the venue", maxLength = 15, example = "+38640123456")
    private String contactPhone;

    @ElementCollection(fetch = FetchType.LAZY)
    @Schema(description = "List of photo URLs for the venue")
    private List<String> photos;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type/category of the venue", example = "CONFERENCE_HALL")
    private VenueType venueType;

    @NotNull(message = "userId of owner must be given")
    @Schema(description = "Identifier of the owner of the venue", example = "42")
    private Long ownerId;

    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Average rating cannot exceed 5")
    @Schema(description = "Average rating of the venue", example = "4.5")
    private Double averageRating;

    @Min(value = 0, message = "Rating count cannot be negative")
    @Schema(description = "Number of ratings received by the venue", example = "10")
    private Integer ratingCount;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Schema(description = "List of ratings for the venue")
    private List<Rating> ratings = new ArrayList<>();

    @Schema(description = "Possible statuses of a venue")
    public enum VenueStatus {
        AVAILABLE,
        UNAVAILABLE
    }

    @Schema(description = "Possible types of venues")
    public enum VenueType {
        CONFERENCE_HALL,
        OUTDOOR_AREA,
        BANQUET_HALL,
        MEETING_ROOM,
        EVENT_SPACE,
        AUDITORIUM,
        WEDDING_HALL,
        SPORTS_ARENA,
        EXHIBITION_CENTER,
        THEATER,
        COWORKING_SPACE,
        ROOFTOP_TERRACE,
        PRIVATE_VILLA,
        BEACHFRONT_AREA,
        FARMHOUSE,
        CLUBHOUSE,
        STUDIO_SPACE,
        OTHER
    }
    
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
