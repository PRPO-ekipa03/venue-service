package si.uni.prpo.group03.venueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import si.uni.prpo.group03.venueservice.dto.CreateRatingDTO;
import si.uni.prpo.group03.venueservice.dto.CreateVenueDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseRatingDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueBasicDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateVenueDTO;
import si.uni.prpo.group03.venueservice.exception.ConcurrentUpdateException;
import si.uni.prpo.group03.venueservice.exception.VenueNotFoundException;
import si.uni.prpo.group03.venueservice.model.Venue;
import si.uni.prpo.group03.venueservice.dto.ResponseVenueDTO;
import si.uni.prpo.group03.venueservice.service.interfaces.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.sql.Timestamp;

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
            @RequestHeader("X-User-Id") String xUserId
    ) {
        // Convert the userId from header to a Long
        Long ownerId = Long.parseLong(xUserId);

        // Pass ownerId to the service layer
        ResponseVenueDTO createdVenue = venueService.createVenue(venueDTO, ownerId);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED);
    }

    // Update an existing venue
    @PutMapping("/{venueId}")
    public ResponseEntity<ResponseVenueDTO> updateVenue(
            @PathVariable Long venueId,
            @RequestBody @Valid UpdateVenueDTO venueDTO
    ) {
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

    @GetMapping
    public ResponseEntity<Page<ResponseVenueBasicDTO>> getAllVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ResponseVenueBasicDTO> venues = venueService.getAllVenues(page, size);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    // Find venues by location
    @GetMapping("/search")
    public ResponseEntity<List<ResponseVenueDTO>> findVenuidesByLocation(@RequestParam String location) {
        List<ResponseVenueDTO> venues = venueService.findVenuesByLocation(location);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    // Find venues by owner ID
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ResponseVenueDTO>> findVenuesByOwnerId(@PathVariable Long ownerId) {
        List<ResponseVenueDTO> venues = venueService.findVenuesByOwnerId(ownerId);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    // Add a rating
    @PostMapping("/{venueId}/ratings")
    public ResponseEntity<ResponseRatingDTO> addRating(
            @PathVariable Long venueId,
            @RequestBody @Valid CreateRatingDTO createRatingDTO,
            @RequestHeader("X-User-Id") String xUserId
    ) {
        try {
            Long userId = Long.parseLong(xUserId);
            ResponseRatingDTO response = venueService.addRating(venueId, createRatingDTO, userId);
            return ResponseEntity.ok(response);
        } catch (VenueNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (ConcurrentUpdateException e) {
            return ResponseEntity.status(409).body(null);
        }
    }

    @GetMapping("/{venueId}/ratings")
    public ResponseEntity<List<ResponseRatingDTO>> getRatingsByVenueId(
            @PathVariable Long venueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ResponseRatingDTO> ratings = venueService.getRatingsByVenueId(venueId, page, size);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ResponseVenueBasicDTO>> getAvailableVenues(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Venue.VenueType venueType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservedDate
    ) {
        // Convert reservedDate from LocalDateTime to Timestamp, or null if not provided
        Timestamp timestamp = reservedDate != null 
        ? Timestamp.valueOf(reservedDate.atStartOfDay()) 
        : null;
        List<ResponseVenueBasicDTO> venues = venueService.findAvailableVenues(location, venueType, timestamp);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }
}
