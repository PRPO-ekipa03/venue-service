package si.uni.prpo.group03.venueservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues", indexes = {
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_venue_type", columnList = "venueType"),
    @Index(name = "idx_status", columnList = "status")
})
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name="OPTLOCK")
    private Integer version; // Required for optimistic locking

    @Column(nullable = false)
    @NotBlank(message = "Venue name is required")
    @Size(max = 100)
    private String name;

    @Column(nullable = true)
    @Size(max = 2000)
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Location is required")
    private String location; //broader, like Lubljana

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
    private String address; //adress, Preš 10

    @Column(nullable = false)
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "venue_amenities", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status must be provided")
    private VenueStatus status;

    @Column(precision = 5, scale = 2) // Precision for currency format
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal pricePerDay;

    private Timestamp openingTime;

    private Timestamp closingTime;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Contact email is required")
    private String contactEmail;

    @Size(max = 15, message = "Contact phone number should not exceed 15 characters")
    private String contactPhone;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> photos;

    @Enumerated(EnumType.STRING)
    private VenueType venueType;

    @NotNull(message = "userId of owner must be given")
    private Long ownerId;

    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Average rating cannot exceed 5")
    private Double averageRating;

    @Min(value = 0, message = "Rating count cannot be negative")
    private Integer ratingCount;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    // Enum for Venue Status
    public enum VenueStatus {
        AVAILABLE,
        UNAVAILABLE
    }

    // Enum for Venue Type
    public enum VenueType {
        CONFERENCE_HALL,
        OUTDOOR_AREA,
        BANQUET_HALL,
        MEETING_ROOM,
        AUDITORIUM
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
