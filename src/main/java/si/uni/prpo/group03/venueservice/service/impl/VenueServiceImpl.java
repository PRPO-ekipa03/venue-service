package si.uni.prpo.group03.venueservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.OptimisticLockException;
import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueBasicDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;
import si.uni.prpo.group03.venueservice.model.Rating;
import si.uni.prpo.group03.venueservice.model.Venue;
import si.uni.prpo.group03.venueservice.repository.VenueRepository;
import si.uni.prpo.group03.venueservice.repository.RatingRepository;
import si.uni.prpo.group03.venueservice.service.interfaces.VenueService;
import si.uni.prpo.group03.venueservice.client.UserServiceClient;
import si.uni.prpo.group03.venueservice.mapper.VenueMapper;
import si.uni.prpo.group03.venueservice.mapper.RatingMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import si.uni.prpo.group03.venueservice.specifications.VenueSpecifications;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;


import si.uni.prpo.group03.venueservice.exception.ConcurrentUpdateException;
import si.uni.prpo.group03.venueservice.exception.VenueNotFoundException;

@Service
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final RatingRepository ratingRepository;
    private final VenueMapper venueMapper;
    private final UserServiceClient userServiceClient;
    private RatingMapper ratingMapper;
    private static final String VENUE_NOT_FOUND = "Venue not found";

    @Autowired
    public VenueServiceImpl(UserServiceClient userServiceClient, VenueRepository venueRepository, VenueMapper venueMapper, RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
        this.ratingMapper = ratingMapper;
        this.ratingRepository = ratingRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ResponseVenueDTO createVenue(CreateVenueDTO venueDTO, Long ownerId) {
        Venue venue = venueMapper.toEntity(venueDTO);
        venue.setOwnerId(ownerId);  // Set the ownerId here
        Venue savedVenue = venueRepository.save(venue);
        return venueMapper.toResponseDTO(savedVenue);
    }

    @Override
    public ResponseVenueDTO updateVenue(Long venueId, UpdateVenueDTO venueDTO) {
        boolean updated = false;
        int retries = 3;  // Set the number of retry attempts
        int delayMillis = 50;  // Delay in milliseconds between retries

        while (!updated && retries > 0) {
            try {
                // Fetch the existing venue, or throw an exception if not found
                Venue existingVenue = venueRepository.findById(venueId)
                        .orElseThrow(() -> new VenueNotFoundException(VENUE_NOT_FOUND));
                
                // Use the mapper to update the existing venue with data from the DTO
                venueMapper.updateEntityFromDto(venueDTO, existingVenue);

                // Save the updated venue
                Venue updatedVenue = venueRepository.save(existingVenue);

                updated = true;  // Mark as updated to exit the loop successfully

                // Convert to ResponseVenueDTO and return
                return venueMapper.toResponseDTO(updatedVenue);
            } catch (OptimisticLockException e) {
                retries--;
                if (retries == 0) {
                    throw new ConcurrentUpdateException("Failed to update venue due to concurrent updates. Please try again later.", e);
                }
                // Optionally, add a short delay here before retrying, if needed
            }
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();  // Restore the interrupted status
                throw new ConcurrentUpdateException("Update interrupted during retry delay.", ie);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteVenue(Long venueId) {
        if (!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException(VENUE_NOT_FOUND);
        }
        venueRepository.deleteById(venueId);
    }

    @Override
    public ResponseVenueDTO getVenueById(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException(VENUE_NOT_FOUND));
        return venueMapper.toResponseDTO(venue);
    }

    @Override
    public Page<ResponseVenueBasicDTO> getAllVenues(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return venueRepository.findAll(pageable)
                .map(venueMapper::toBasicDTO); // Use the new toBasicDTO mapping method
    }

    @Override
    public List<ResponseVenueDTO> findVenuesByLocation(String location) {
        return venueRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(venueMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseVenueDTO> findVenuesByOwnerId(Long ownerId) {
        return venueRepository.findByOwnerId(ownerId)
                .stream()
                .map(venueMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseRatingDTO addRating(Long venueId, CreateRatingDTO createRatingDTO, Long userId) {
        boolean updated = false;
        int retries = 3;
        int delayMillis = 50;  // Delay in milliseconds between retries

        while (!updated && retries > 0) {
            try {
                Venue venue = venueRepository.findById(venueId)
                        .orElseThrow(() -> new VenueNotFoundException(VENUE_NOT_FOUND));

                // Fetch user's full name from UserServiceClient
                String fullName = userServiceClient.getUserFullName(userId);

                // Map CreateRatingDTO to Rating and set the venue and userId
                Rating rating = ratingMapper.toEntity(createRatingDTO);
                rating.setVenue(venue);
                rating.setUserId(userId);  // Set userId from JWT
                rating.setFullName(fullName);  // Set the full name
                ratingRepository.save(rating);

                // Handle the case where ratingCount is null by treating it as 0 initially
                int currentRatingCount = (venue.getRatingCount() != null) ? venue.getRatingCount() : 0;
                double currentAverageRating = (venue.getAverageRating() != null) ? venue.getAverageRating() : 0.0;

                // Calculate new average rating and update count
                int newRatingCount = currentRatingCount + 1;
                double newAverageRating = ((currentAverageRating * currentRatingCount) + createRatingDTO.getRatingValue()) / newRatingCount; 

                venue.setRatingCount(newRatingCount);
                venue.setAverageRating(newAverageRating);
                venueRepository.save(venue);

                updated = true;

                // Use RatingMapper to create ResponseRatingDTO
                return ratingMapper.toResponseRatingDTO(rating, venue);
            } catch (OptimisticLockException e) {
                retries--;
                if (retries == 0) {
                    throw new ConcurrentUpdateException("Failed to update rating due to concurrent updates. Please try again later.", e);
                }
            }
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();  // Restore the interrupted status
                throw new ConcurrentUpdateException("Update interrupted during retry delay.", ie);
            }
        }
        return null;
    }

    @Override
    public List<ResponseRatingDTO> getRatingsByVenueId(Long venueId, int page, int size) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException(VENUE_NOT_FOUND));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Rating> ratingPage = ratingRepository.findByVenueId(venueId, pageable);

        return ratingPage.stream()
                .map(rating -> ratingMapper.toResponseRatingDTO(rating, venue))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseVenueBasicDTO> findAvailableVenues(String location, Venue.VenueType venueType, Timestamp reservedDate) {
 
        // Create a Specification based on the provided parameters
        Specification<Venue> spec = VenueSpecifications.findAvailableVenues(location, venueType, reservedDate);

        // Use findAll with the Specification to retrieve a list of Venue entities
        List<Venue> venues = venueRepository.findAll(spec);

        // Map each Venue entity to a ResponseVenueBasicDTO using your mapper
        return venues.stream()
                     .map(venueMapper::toBasicDTO)
                     .collect(Collectors.toList());
    }
}
