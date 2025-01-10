package si.uni.prpo.group03.venueservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.NullValuePropertyMappingStrategy;

import si.uni.prpo.group03.venueservice.dto.CreateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseReservationDTO;
import si.uni.prpo.group03.venueservice.model.Reservation;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    // Mapping from CreateReservationDTO to Reservation entity
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "status", defaultValue = "PENDING")  // Default status is ACTIVE
    Reservation toEntity(CreateReservationDTO createReservationDTO);

    // Mapping from UpdateReservationDTO to Reservation for partial updates
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venueId", ignore = true)  // venueId is immutable in this update
    @Mapping(target = "eventId", ignore = true)  // eventId is immutable in this update
    @Mapping(target = "userId", ignore = true) // userId is immutable in this update
    void updateEntityFromDto(UpdateReservationDTO updateReservationDTO, @MappingTarget Reservation reservation);

    // Mapping from Reservation entity to ResponseReservationDTO
    ResponseReservationDTO toResponseDTO(Reservation reservation);
}
