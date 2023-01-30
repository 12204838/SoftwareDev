package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IReservationRepository;

@CrossOrigin(maxAge = 3600)
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
	
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/update/{id}")
	public ResponseDto updateReservationById(@PathVariable long id, @RequestBody Reservation reservation) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();
		
		reservationDb.setUser(reservation.getUser());
		reservationDb.setBook(reservation.getBook());
		reservationDb.setApproved(reservation.isApproved());
		
		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	@DeleteMapping("reservations/delete/{id}")
	public ResponseDto deleteReservationById(@PathVariable long id) {
		reservationRepo.deleteById(id);
		return new ResponseDto();	
	}
	
}
