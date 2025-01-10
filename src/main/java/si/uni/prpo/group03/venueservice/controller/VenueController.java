package si.uni.prpo.group03.venueservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import si.uni.prpo.group03.venueservice.dto.*;
import si.uni.prpo.group03.venueservice.exception.ConcurrentUpdateException;
import si.uni.prpo.group03.venueservice.exception.VenueNotFoundException;
import si.uni.prpo.group03.venueservice.model.Venue;
import si.uni.prpo.group03.venueservice.service.interfaces.VenueService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Venues", description = "Venue Management")
@RestController
@RequestMapping("/api/venues")
@Validated
public class VenueController {

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @Operation(
        summary = "Create a new venue",
        description = "Creates a new venue with the provided details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venue created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid venue data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ResponseVenueDTO> createVenue(
            @Parameter(description = "Venue details", required = true)
            @RequestBody @Valid CreateVenueDTO venueDTO,
            @Parameter(description = "User ID of the venue owner", required = true)
            @RequestHeader("X-User-Id") String xUserId
    ) {
        Long ownerId = Long.parseLong(xUserId);
        ResponseVenueDTO createdVenue = venueService.createVenue(venueDTO, ownerId);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing venue",
        description = "Updates the venue identified by venueId with new details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid venue data or ID"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "409", description = "Conflict due to concurrent update"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{venueId}")
    public ResponseEntity<ResponseVenueDTO> updateVenue(
            @Parameter(description = "ID of the venue to update", required = true)
            @PathVariable Long venueId,
            @Parameter(description = "Updated venue details", required = true)
            @RequestBody @Valid UpdateVenueDTO venueDTO
    ) {
        ResponseVenueDTO updatedVenue = venueService.updateVenue(venueId, venueDTO);
        return new ResponseEntity<>(updatedVenue, HttpStatus.OK);
    }

    @Operation(
        summary = "Delete a venue",
        description = "Deletes the venue identified by venueId."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{venueId}")
    public ResponseEntity<Void> deleteVenue(
            @Parameter(description = "ID of the venue to delete", required = true)
            @PathVariable Long venueId) {
        venueService.deleteVenue(venueId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Get a venue by ID",
        description = "Retrieves the details of the venue with the specified ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{venueId}")
    public ResponseEntity<ResponseVenueDTO> getVenueById(
            @Parameter(description = "ID of the venue to retrieve", required = true)
            @PathVariable Long venueId) {
        ResponseVenueDTO venue = venueService.getVenueById(venueId);
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @Operation(
        summary = "Get all venues",
        description = "Retrieves a paginated list of venues."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<ResponseVenueBasicDTO>> getAllVenues(
            @Parameter(description = "Page number for pagination", required = false, example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination", required = false, example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ResponseVenueBasicDTO> venues = venueService.getAllVenues(page, size);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @Operation(
        summary = "Find venues by location",
        description = "Searches for venues containing the specified location string."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ResponseVenueDTO>> findVenuidesByLocation(
            @Parameter(description = "Location to search for", required = true)
            @RequestParam String location) {
        List<ResponseVenueDTO> venues = venueService.findVenuesByLocation(location);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @Operation(
        summary = "Find venues by owner ID",
        description = "Retrieves venues owned by the specified user."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/owner")
    public ResponseEntity<List<ResponseVenueBasicDTO>> findVenuesByOwnerId(
            @Parameter(description = "User ID of the owner from header", required = true)
            @RequestHeader("X-User-Id") String xUserId) {
        Long ownerId = Long.parseLong(xUserId);
        List<ResponseVenueBasicDTO> venues = venueService.findVenuesByOwnerId(ownerId);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @Operation(
        summary = "Add a rating to a venue",
        description = "Adds a rating for the specified venue by the user."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rating added successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "409", description = "Conflict due to concurrent update"),
        @ApiResponse(responseCode = "400", description = "Invalid rating data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{venueId}/ratings")
    public ResponseEntity<ResponseRatingDTO> addRating(
            @Parameter(description = "ID of the venue to rate", required = true)
            @PathVariable Long venueId,
            @Parameter(description = "Rating details", required = true)
            @RequestBody @Valid CreateRatingDTO createRatingDTO,
            @Parameter(description = "User ID from header", required = true)
            @RequestHeader("X-User-Id") String xUserId
    ) {
        try {
            Long userId = Long.parseLong(xUserId);
            ResponseRatingDTO response = venueService.addRating(venueId, createRatingDTO, userId);
            return ResponseEntity.ok(response);
        } catch (VenueNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConcurrentUpdateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @Operation(
        summary = "Get ratings for a venue",
        description = "Retrieves paginated ratings for the specified venue."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{venueId}/ratings")
    public ResponseEntity<List<ResponseRatingDTO>> getRatingsByVenueId(
            @Parameter(description = "ID of the venue", required = true)
            @PathVariable Long venueId,
            @Parameter(description = "Page number for pagination", required = false, example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination", required = false, example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ResponseRatingDTO> ratings = venueService.getRatingsByVenueId(venueId, page, size);
        return ResponseEntity.ok(ratings);
    }

    @Operation(
        summary = "Get available venues", 
        description = "Retrieves venues that are available based on optional filters like location, venue type, and reserved date."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Available venues retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/available")
    public ResponseEntity<List<ResponseVenueBasicDTO>> getAvailableVenues(
            @Parameter(description = "Location filter for venues")
            @RequestParam(required = false) String location,
            @Parameter(
                description = "Type of venue",
                required = false,
                example = "CONCERT" // adjust based on actual enum values if necessary
            )
            @RequestParam(required = false) Venue.VenueType venueType,
            @Parameter(description = "Reserved date filter in ISO format (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservedDate
    ) {
        Timestamp timestamp = reservedDate != null 
            ? Timestamp.valueOf(reservedDate.atStartOfDay()) 
            : null;
        List<ResponseVenueBasicDTO> venues = venueService.findAvailableVenues(location, venueType, timestamp);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }
}
