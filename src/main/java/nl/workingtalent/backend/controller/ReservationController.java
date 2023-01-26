package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IReservationRepository;

@RestController
public class ReservationController {
	
	@Autowired
	private IReservationRepository reservationRepo;
	
	@RequestMapping("reservations")
	public Stream<ReservationDto> reservations() {
		List<Reservation> reservations = reservationRepo.findAll();
		return reservations.stream().map(reservation -> new ReservationDto(reservation));
	}
	
	@RequestMapping( method = RequestMethod.POST, value="reservations/save")
	public ResponseDto saveReservation(@RequestBody Reservation reservation) {
		reservationRepo.save(reservation);
		
		return new ResponseDto();
	}
}
