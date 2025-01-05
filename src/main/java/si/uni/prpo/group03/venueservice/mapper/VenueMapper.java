package si.uni.prpo.group03.venueservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueBasicDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;
import si.uni.prpo.group03.venueservice.model.Venue;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VenueMapper {

    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    // Mapping from CreateVenueDTO to Venue entity for creation
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "ownerId", ignore = true) // given by JWT
    @Mapping(target = "averageRating", ignore = true)  // Set by the system
    @Mapping(target = "ratingCount", ignore = true)    // Set by the system
    Venue toEntity(CreateVenueDTO createVenueDTO);

    // Mapping from UpdateVenueDTO to Venue for partial updates
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "averageRating", ignore = true)  // Not updatable through DTO
    @Mapping(target = "ratingCount", ignore = true)    // Not updatable through DTO
    void updateEntityFromDto(UpdateVenueDTO updateVenueDTO, @MappingTarget Venue venue);

    // Mapping from Venue entity to ResponseVenueDTO
    ResponseVenueDTO toResponseDTO(Venue venue);

    // Mapping from Venue entity to BasicVenueDTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "photos", source = "photos")
    @Mapping(target = "averageRating", source = "averageRating")
    ResponseVenueBasicDTO toBasicDTO(Venue venue);
}
