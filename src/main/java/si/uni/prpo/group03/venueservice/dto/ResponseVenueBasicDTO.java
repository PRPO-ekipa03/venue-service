package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Basic response Data Transfer Object for venue details.")
public class ResponseVenueBasicDTO {

    @Schema(description = "Unique identifier of the venue", example = "1")
    private Long id;

    @Schema(description = "Name of the venue", example = "Grand Hall")
    private String name;

    @Schema(description = "Location of the venue", example = "Ljubljana")
    private String location;

    @Schema(description = "Short description of the venue", example = "A spacious hall suitable for events.")
    private String description;

    @Schema(description = "List of photo URLs for the venue", example = "[\"http://example.com/photo1.jpg\", \"http://example.com/photo2.jpg\"]")
    private List<String> photos;

    @Schema(description = "Average rating of the venue", example = "4.5")
    private Double averageRating;

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
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
