package si.uni.prpo.group03.venueservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.model.Rating;
import si.uni.prpo.group03.venueservice.model.Venue;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "userId", ignore = true) 
    @Mapping(target = "venue", ignore = true)  // Venue will be set separately
    @Mapping(target = "timestamp", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    Rating toEntity(CreateRatingDTO createRatingDTO);

    // Mapping from Rating and Venue to ResponseRatingDTO
    @Mapping(source = "rating.id", target = "ratingId")
    @Mapping(source = "venue.id", target = "venueId")
    @Mapping(source = "venue.averageRating", target = "newAverageRating")
    @Mapping(source = "venue.ratingCount", target = "newRatingCount")
    @Mapping(source = "rating.comment", target = "comment")
    ResponseRatingDTO toResponseRatingDTO(Rating rating, Venue venue);
}
