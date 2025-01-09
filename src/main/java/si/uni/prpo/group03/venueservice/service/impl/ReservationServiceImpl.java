package si.uni.prpo.group03.venueservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import si.uni.prpo.group03.venueservice.dto.CreateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseReservationDTO;
import si.uni.prpo.group03.venueservice.model.Reservation;
import si.uni.prpo.group03.venueservice.model.Reservation.ReservationStatus;
import si.uni.prpo.group03.venueservice.repository.ReservationRepository;
import si.uni.prpo.group03.venueservice.repository.VenueRepository;
import si.uni.prpo.group03.venueservice.service.interfaces.ReservationService;
import si.uni.prpo.group03.venueservice.mapper.ReservationMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import si.uni.prpo.group03.venueservice.exception.ReservationNotFoundException;
import si.uni.prpo.group03.venueservice.exception.ReservationConflictException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class ReservationServiceImpl implements ReservationService {

    private final VenueRepository venueRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private static final String RESERVATION_NOT_FOUND = "Reservation not found";

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, VenueRepository venueRepository) {
        this.reservationRepository = reservationRepository;
        this.venueRepository = venueRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ResponseReservationDTO createReservation(CreateReservationDTO reservationDTO) {
        // Check if the venue exists
        if (!venueRepository.existsById(reservationDTO.getVenueId())) {
            throw new EntityNotFoundException("The specified venue does not exist.");
        }

        // Calculate the start and end of the day for the reservation date
        LocalDate reservationDate = reservationDTO.getReservedDate().toLocalDateTime().toLocalDate();
        Timestamp startOfDay = Timestamp.valueOf(reservationDate.atStartOfDay()); // 00:00:00 on that date
        Timestamp endOfDay = Timestamp.valueOf(reservationDate.atTime(23, 59, 59)); // 23:59:59 on that date

        // Check if there's already a reservation for the same date (daily basis)
        if (!reservationRepository.findByVenueIdAndReservedDateBetween(
                reservationDTO.getVenueId(), startOfDay, endOfDay).isEmpty()) {
            throw new ReservationConflictException("A reservation already exists on this date for the specified venue.");
        }
        
        // Proceed with reservation creation
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponseDTO(savedReservation);
    }

    @Override
    public List<Timestamp> getReservationDatesByVenueId(Long venueId) {
        return reservationRepository.findByVenueId(venueId)
                .stream()
                .map(Reservation::getReservedDate)  // Extract only the reserved date
                .collect(Collectors.toList());
    }

    @Override
    public ResponseReservationDTO updateReservation(Long reservationId, UpdateReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));
        
        reservationMapper.updateEntityFromDto(reservationDTO, existingReservation);
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return reservationMapper.toResponseDTO(updatedReservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND);
        }
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public ResponseReservationDTO getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));
        return reservationMapper.toResponseDTO(reservation);
    }

    @Override
    public List<ResponseReservationDTO> getReservationsByVenueId(Long venueId) {
        return reservationRepository.findByVenueId(venueId)
                .stream()
                .map(reservationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseReservationDTO> getReservationsByDateRange(Long venueId, Timestamp startDate, Timestamp endDate) {
        return reservationRepository.findByVenueIdAndReservedDateBetween(venueId, startDate, endDate)
                .stream()
                .map(reservationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = "paymentConfirmedQueue")
    public void handlePaymentConfirmed(Long reservationId) {
        // Update the reservation status based on reservationId
        // e.g., change status from PENDING to ACTIVE
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus(ReservationStatus.ACTIVE);
            reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation not found for ID: " + reservationId);
        }

    }

    @Override
    public List<ResponseReservationDTO> getReservationsByUserId(Long userId) {
        // Assuming your repository has a method findByUserId
        List<Reservation> reservations = reservationRepository.findByUserId(userId);

        // Map Reservation entities to DTOs
        return reservations.stream()
                           .map(reservationMapper::toResponseDTO)
                           .collect(Collectors.toList());
    }
}
