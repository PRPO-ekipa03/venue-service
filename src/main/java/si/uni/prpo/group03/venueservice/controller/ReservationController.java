package si.uni.prpo.group03.venueservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import si.uni.prpo.group03.venueservice.dto.CreateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseReservationDTO;
import si.uni.prpo.group03.venueservice.service.interfaces.ReservationService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Reservations", description = "Reservation Management")
@RestController
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(
        summary = "Create a new reservation",
        description = "Creates a new reservation with the provided details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid reservation data"),
        @ApiResponse(responseCode = "404", description = "Venue not found"),
        @ApiResponse(responseCode = "409", description = "Reservation conflict on the specified date"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ResponseReservationDTO> createReservation(
            @Parameter(description = "Reservation details", required = true)
            @RequestBody @Valid CreateReservationDTO reservationDTO) {
        ResponseReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing reservation",
        description = "Updates the reservation identified by reservationId with new details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid reservation data or ID"),
        @ApiResponse(responseCode = "404", description = "Reservation not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> updateReservation(
            @Parameter(description = "ID of the reservation to update", required = true)
            @PathVariable Long reservationId,
            @Parameter(description = "Updated reservation details", required = true)
            @RequestBody @Valid UpdateReservationDTO reservationDTO) {
        ResponseReservationDTO updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @Operation(
        summary = "Cancel a reservation",
        description = "Cancels the reservation identified by reservationId."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reservation cancelled successfully"),
        @ApiResponse(responseCode = "404", description = "Reservation not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "ID of the reservation to cancel", required = true)
            @PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Get a reservation by ID",
        description = "Retrieves the details of the reservation with the specified ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Reservation not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> getReservationById(
            @Parameter(description = "ID of the reservation to retrieve", required = true)
            @PathVariable Long reservationId) {
        ResponseReservationDTO reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @Operation(
        summary = "Get all reservations for a specific venue",
        description = "Retrieves all reservations for the venue specified by venueId."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/venues/{venueId}")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByVenueId(
            @Parameter(description = "ID of the venue", required = true)
            @PathVariable Long venueId) {
        List<ResponseReservationDTO> reservations = reservationService.getReservationsByVenueId(venueId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(
        summary = "Get reservations for a venue within a date range",
        description = "Retrieves reservations for a specified venue that fall within the given date range."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid date format or parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/venues/{venueId}/range")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByDateRange(
            @Parameter(description = "ID of the venue", required = true)
            @PathVariable Long venueId,
            @Parameter(description = "Start date-time in ISO format", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date-time in ISO format", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Timestamp startTimestamp = Timestamp.valueOf(startDate);
        Timestamp endTimestamp = Timestamp.valueOf(endDate);
        List<ResponseReservationDTO> reservations = reservationService.getReservationsByDateRange(venueId, startTimestamp, endTimestamp);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(
        summary = "Get reservations by user ID",
        description = "Retrieves all reservations made by the user specified in the X-User-Id header."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByUserId(
            @Parameter(description = "User ID from header", required = true)
            @RequestHeader("X-User-Id") String xUserId) {
        Long userId = Long.parseLong(xUserId);
        List<ResponseReservationDTO> reservations = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
