package nl.workingtalent.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.ReservationApproveDto;
import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.dto.ReservationViewCopyDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.BorrowedCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;
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
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;

	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;

	
	@RequestMapping("reservations")
	public Stream<ReservationDto> reservations() {
		List<Reservation> reservations = reservationRepo.findAll();
		
		return reservations.stream().map(reservation -> new ReservationDto(reservation));
	}
	
	@RequestMapping(method = RequestMethod.POST, value="reservations/save")
	public ResponseDto saveReservation(@RequestBody Reservation reservation) {
		reservationRepo.save(reservation);
		
		return new ResponseDto();
	}
	
	/**
	 * This method saves a reservation based on the id of the book and the id of the user that
	 * are passed in the request body.
	 * 
	 * @param ReservationDto the Data Transfer Object that contains the bookId and userId that
	 * are necessary for the reservation.
	 * @return a Response Data Transfer Object to show if everything worked as intended 
	 */
	
	// onnodig waarschijnlijk verwijderen.
	@RequestMapping(method = RequestMethod.POST, value = "reservation/savebybookanduserid")
	public ResponseDto saveReservationByBookAndUserId(@RequestBody ReservationDto reservationDto) {
		Optional<Book> optionalBook = bookRepo.findById(reservationDto.getBookId());
		Optional<User> optionalUser = userRepo.findById(reservationDto.getUserId());
		
		if (optionalBook.isEmpty()) {
			return new ResponseDto("The book you are trying to reserve does not exist.");
		}
		
		else if (optionalUser.isEmpty()) {
			return new ResponseDto("This user does not exist.");
		}
		
		Reservation reservation = new Reservation();
		reservation.setBook(optionalBook.get());
		reservation.setUser(optionalUser.get());
		reservation.setApproved(reservationDto.isApproved());
		
		reservationRepo.save(reservation);
		
		return new ResponseDto();
	}
		
	
	@DeleteMapping("reservation/delete/{id}")
	public ResponseDto deleteReservationById(@PathVariable long id) {
		reservationRepo.deleteById(id);
		return new ResponseDto();	
	}
	// Approves reservation based on reservation id.
	// Checks for admin rights. If admin is true it approves the reservation.
	@PutMapping("reservation/{id}/approve")
	public ResponseDto approveReservation(@PathVariable long id, @RequestBody ReservationApproveDto dto,
			@RequestHeader("Authorization") String token) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This reservation does not exist yet.");
		}
		if (optionalUser.get().isAdmin()) {
		Reservation reservationDb = optional.get();
		if (!reservationDb.isApproved()) {
			// approve
			reservationDb.setApproved(true);
			reservationRepo.save(reservationDb);

			// Book copy aanmaken
			Optional<BookCopy> bookCopyOptional = this.bookCopyRepo.findById(dto.getBookCopyId());
			Optional<User> userOptional = this.userRepo.findById(dto.getUserId());
			if (bookCopyOptional.isPresent() && userOptional.isPresent()) {
				BorrowedCopy borrowedCopy = new BorrowedCopy();
				borrowedCopy.setBookCopy(bookCopyOptional.get());
				borrowedCopy.setStartDate(LocalDate.now());
				borrowedCopy.setUser(userOptional.get());
				
				borrowedCopyRepo.save(borrowedCopy);
			}
		}
		return new ResponseDto();
		}
		else {
			return null;
		}
		
	}
	
	// optional onnodig
	// Denies reservation based on resId and checks for admin rights. If admin = true reservation is denied.
	@DeleteMapping("reservation/{id}/deny")
	public ResponseDto denyReservation(@PathVariable long id,
			@RequestHeader("Authorization") String token) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		 if (optionalUser.get().isAdmin()) {
			reservationRepo.deleteById(id);
			return new ResponseDto();
		}
		else {
			return null;
		}

		
		
		
	}
	
	//Obtains userid and bookid based on reservationid, checks for admin rights. If admin= true the userid and bookid are returned. 
	@GetMapping("reservation/{id}/getbookiduserid")
	public ReservationViewCopyDto viewReservationBookCopies(@PathVariable long id,
			@RequestHeader("Authorization") String token) {
		Optional<Reservation> optional = reservationRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		
		if (optionalUser.get().isAdmin()) {
			ReservationViewCopyDto r = new ReservationViewCopyDto(optional.get());
			return r;
		}
		else {
			return null;
		}
		//if (optional.isEmpty()) {
			//return new ResponseDto("This reservation does not exist yet.");
		//}
		
		
		
	}
}
