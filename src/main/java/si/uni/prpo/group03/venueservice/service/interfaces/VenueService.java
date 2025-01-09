package si.uni.prpo.group03.venueservice.service.interfaces;

import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueBasicDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.model.Venue;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;
import java.sql.Timestamp;

import java.util.List;

import org.springframework.data.domain.Page;

public interface VenueService {
    ResponseVenueDTO createVenue(CreateVenueDTO venueDTO, Long ownerId);
    ResponseVenueDTO updateVenue(Long venueId, UpdateVenueDTO venueDTO);
    void deleteVenue(Long venueId);
    ResponseVenueDTO getVenueById(Long venueId);
    Page<ResponseVenueBasicDTO> getAllVenues(int page, int size);
    List<ResponseVenueDTO> findVenuesByLocation(String location);
    List<ResponseVenueBasicDTO> findVenuesByOwnerId(Long ownerId);
    ResponseRatingDTO addRating(Long venueId, CreateRatingDTO createRatingDTO, Long userId);
    List<ResponseRatingDTO> getRatingsByVenueId(Long venueId, int page, int size);
    List<ResponseVenueBasicDTO> findAvailableVenues(String location, Venue.VenueType venueType, Timestamp reservedDate);
}
