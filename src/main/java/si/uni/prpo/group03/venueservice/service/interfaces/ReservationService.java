package si.uni.prpo.group03.venueservice.service.interfaces;

import si.uni.prpo.group03.venueservice.dto.CreateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.UpdateReservationDTO;
import si.uni.prpo.group03.venueservice.dto.ResponseReservationDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationService {
    ResponseReservationDTO createReservation(CreateReservationDTO reservationDTO);
    ResponseReservationDTO updateReservation(Long reservationId, UpdateReservationDTO reservationDTO);
    void cancelReservation(Long reservationId);
    ResponseReservationDTO getReservationById(Long reservationId);
    List<ResponseReservationDTO> getReservationsByVenueId(Long venueId);
    List<Timestamp> getReservationDatesByVenueId(Long venueId);
    List<ResponseReservationDTO> getReservationsByDateRange(Long venueId, Timestamp startDate, Timestamp endDate);
}
