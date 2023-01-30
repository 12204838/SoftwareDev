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

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookRepository;
import nl.workingtalent.backend.repository.IReservationRepository;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReservationController {
	
	@Autowired
	private IReservationRepository reservationRepo;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IBookRepository bookRepo;

	
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
	
	/**
	 * This method saves a bookCopy based on the id of the book.
	 * 
	 * @param BookCopyDto the Data Transfer Object that represents the book which contains the bookId
	 * @return a Response Data Transfer Object to show if everything worked as intended 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "reservation/savebybookanduserid")
	public ResponseDto saveReservationByBookAndUserId(@RequestBody ReservationDto reservationDto) {
		Optional<Book> optionalBook = bookRepo.findById(reservationDto.getBookId());
		Optional<User> optionalUser = userRepo.findById(reservationDto.getUserId());
		
		if (optionalBook.isEmpty()) {
			return new ResponseDto("The book of which you try to add copies does not exist.");
		}
		else if (optionalUser.isEmpty()) {
			return new ResponseDto("This user does not exist.");
		}
		
		Reservation reservation = new Reservation();
		reservation.setBook(optionalBook.get());
		reservation.setUser(optionalUser.get());
		
		reservationRepo.save(reservation);
		
		return new ResponseDto();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/update/{id}")
	public ResponseDto updateReservationById(@PathVariable long id, @RequestBody Reservation reservation) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();
		
		reservationDb.setUser(reservation.getUser());
		reservationDb.setBook(reservation.getBook());
		reservationDb.setApproved(reservation.isApproved());
		
		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/updatebybookidanduserid/{id}")
	public ResponseDto updateReservationById(@PathVariable long id, @RequestBody ReservationDto reservationDto) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();
		Optional<Book> optionalBook = bookRepo.findById(reservationDto.getBookId());
		Optional<User> optionalUser = userRepo.findById(reservationDto.getUserId());	
		if (optionalUser.isEmpty()) {
			return new ResponseDto("This user does not exist.");
		}
		reservationDb.setUser(optionalUser.get());
		if (optionalBook.isEmpty()) {
			return new ResponseDto("This book does not exist.");
		}
		reservationDb.setBook(optionalBook.get());
		reservationDb.setApproved(reservationDto.isApproved());
		
		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	//Approved 
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/updateisapproved/{id}")
	public ResponseDto updateIsApprovedById(@PathVariable long id, @RequestBody ReservationDto reservationDto) {
		Optional<Reservation> optional = reservationRepo.findById(id);		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();

		reservationDb.setApproved(reservationDto.isApproved());		
		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	// Update book
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/updatebook/{id}")
	public ResponseDto updateBookById(@PathVariable long id, @RequestBody ReservationDto reservationDto) {
		Optional<Reservation> optional = reservationRepo.findById(id);		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();
		Optional<Book> optionalBook = bookRepo.findById(reservationDto.getBookId());
		
		if (optionalBook.isEmpty()) {
			return new ResponseDto("This book does not exist.");
		}
		reservationDb.setBook(optionalBook.get());

		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	
	// Update User
	@RequestMapping(method = RequestMethod.PUT, value = "reservations/updateuser/{id}")
	public ResponseDto updateUserById(@PathVariable long id, @RequestBody ReservationDto reservationDto) {
		Optional<Reservation> optional = reservationRepo.findById(id);		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		
		Reservation reservationDb = optional.get();
		Optional<User> optionalUser = userRepo.findById(reservationDto.getUserId());
		
		if (optionalUser.isEmpty()) {
			return new ResponseDto("This user does not exist.");
		}
		reservationDb.setUser(optionalUser.get());

		reservationRepo.save(reservationDb);
		
		return new ResponseDto();		
	}
	
	@DeleteMapping("reservations/delete/{id}")
	public ResponseDto deleteReservationById(@PathVariable long id) {
		reservationRepo.deleteById(id);
		return new ResponseDto();	
	}
	
}
