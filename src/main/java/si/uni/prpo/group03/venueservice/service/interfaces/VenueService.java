package si.uni.prpo.group03.venueservice.service.interfaces;

import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;

import java.util.List;

public interface VenueService {
    ResponseVenueDTO createVenue(CreateVenueDTO venueDTO, Long ownerId);
    ResponseVenueDTO updateVenue(Long venueId, UpdateVenueDTO venueDTO);
    void deleteVenue(Long venueId);
    ResponseVenueDTO getVenueById(Long venueId);
    List<ResponseVenueDTO> getAllVenues();
    List<ResponseVenueDTO> findVenuesByLocation(String location);
    List<ResponseVenueDTO> findVenuesByOwnerId(Long ownerId);
    ResponseRatingDTO addRating(Long venueId, CreateRatingDTO createRatingDTO, Long userId);
    List<ResponseRatingDTO> getRatingsByVenueId(Long venueId, int page, int size);
}
