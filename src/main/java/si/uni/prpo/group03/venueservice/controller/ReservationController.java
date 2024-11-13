package si.uni.prpo.group03.venueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import si.uni.prpo.group03.venueservice.dto.CreateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseReservationDTO;
import si.uni.prpo.group03.venueservice.service.interfaces.ReservationService;
import jakarta.validation.Valid;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<ResponseReservationDTO> createReservation(
            @RequestBody @Valid CreateReservationDTO reservationDTO) {
        ResponseReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    // Update an existing reservation
    @PutMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody @Valid UpdateReservationDTO reservationDTO) {
        ResponseReservationDTO updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    // Cancel a reservation
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    // Get a reservation by ID
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> getReservationById(@PathVariable Long reservationId) {
        ResponseReservationDTO reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // Get all reservations for a specific venueId
    @GetMapping("/venues/{venueId}")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByVenueId(@PathVariable Long venueId) {
        List<ResponseReservationDTO> reservations = reservationService.getReservationsByVenueId(venueId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Get reservations for a specific venue within a date range
    @GetMapping("/venues/{venueId}/range")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByDateRange(
            @PathVariable Long venueId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Timestamp startTimestamp = Timestamp.valueOf(startDate);
        Timestamp endTimestamp = Timestamp.valueOf(endDate);

        List<ResponseReservationDTO> reservations = reservationService.getReservationsByDateRange(venueId, startTimestamp, endTimestamp);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
