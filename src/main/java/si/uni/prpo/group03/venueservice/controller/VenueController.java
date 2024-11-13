package si.uni.prpo.group03.venueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.exception.ConcurrentUpdateException;
import si.uni.prpo.group03.venueservice.exception.VenueNotFoundException;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;
import si.uni.prpo.group03.venueservice.service.interfaces.VenueService;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@Valid
public class VenueController {

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // Create a new venue
    @PostMapping
    public ResponseEntity<ResponseVenueDTO> createVenue(
            @RequestBody @Valid CreateVenueDTO venueDTO,
            @RequestParam(value = "ownerId", defaultValue = "1") Long ownerId) {
        // Pass ownerId to the service layer
        ResponseVenueDTO createdVenue = venueService.createVenue(venueDTO, ownerId);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED);
    }

    // Update an existing venue
    @PutMapping("/{venueId}")
    public ResponseEntity<ResponseVenueDTO> updateVenue(
            @PathVariable Long venueId,
            @RequestBody @Valid UpdateVenueDTO venueDTO) {
        ResponseVenueDTO updatedVenue = venueService.updateVenue(venueId, venueDTO);
        return new ResponseEntity<>(updatedVenue, HttpStatus.OK);
    }

    // Delete a venue
    @DeleteMapping("/{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long venueId) {
        venueService.deleteVenue(venueId);
        return ResponseEntity.noContent().build();
    }

    // Get a venue by ID
    @GetMapping("/{venueId}")
    public ResponseEntity<ResponseVenueDTO> getVenueById(@PathVariable Long venueId) {
        ResponseVenueDTO venue = venueService.getVenueById(venueId);
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    // Get all venues
    @GetMapping
    public ResponseEntity<List<ResponseVenueDTO>> getAllVenues() {
        List<ResponseVenueDTO> venues = venueService.getAllVenues();
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    // Find venues by location
    @GetMapping("/search")
    public ResponseEntity<List<ResponseVenueDTO>> findVenuesByLocation(@RequestParam String location) {
        List<ResponseVenueDTO> venues = venueService.findVenuesByLocation(location);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    // Find venues by owner ID
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ResponseVenueDTO>> findVenuesByOwnerId(@PathVariable Long ownerId) {
        List<ResponseVenueDTO> venues = venueService.findVenuesByOwnerId(ownerId);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @PostMapping("/{venueId}/ratings")
    public ResponseEntity<ResponseRatingDTO> addRating(
            @PathVariable Long venueId,
            @RequestBody @Valid CreateRatingDTO createRatingDTO,
            @RequestParam(value = "userId", defaultValue = "1") Long userId) {
        
        try {
            // Call the service layer to add a rating
            ResponseRatingDTO response = venueService.addRating(venueId, createRatingDTO, userId);
            return ResponseEntity.ok(response);
        } catch (VenueNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (ConcurrentUpdateException e) {
            return ResponseEntity.status(409).body(null);  // Conflict status for concurrent update issues
        }
    }

    @GetMapping("/{venueId}/ratings")
    public ResponseEntity<List<ResponseRatingDTO>> getRatingsByVenueId(
            @PathVariable Long venueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<ResponseRatingDTO> ratings = venueService.getRatingsByVenueId(venueId, page, size);
        return ResponseEntity.ok(ratings);
    }
}
