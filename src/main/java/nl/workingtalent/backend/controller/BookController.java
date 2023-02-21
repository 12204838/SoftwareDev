package nl.workingtalent.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.AvailableBookCopyDto;
import nl.workingtalent.backend.dto.AvailableBooksDto;
import nl.workingtalent.backend.dto.BookAvailableDto;
import nl.workingtalent.backend.dto.BookCopyIsbnDto;
import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.ExtendedBookCopyDto;
import nl.workingtalent.backend.dto.MakeReservationDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.SaveBookCopiesDto;
import nl.workingtalent.backend.dto.TotalBookCopiesDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;
import nl.workingtalent.backend.repository.IReservationRepository;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class BookController {

	@Autowired
	private IBookRepository bookRepo;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;

	@Autowired
	private IReservationRepository resRepo;
	
	@RequestMapping("books")
	public Stream<BookAvailableDto> books(@RequestHeader("Authorization") String token) {
		List<Book> books = bookRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return books.stream().map(book -> {
			long noOfBookCopies = bookCopyRepo.countByBookAndWtIdIsGreaterThan(book, 0);
			long noOfNonAvailable = borrowedCopyRepo.countByBookCopyBookAndEndDateIsNull(book);
			
			if (noOfBookCopies - noOfNonAvailable == 0) {
				return new BookAvailableDto(book, false);
			}
			else {
				return new BookAvailableDto(book, true);
			}
		});	
	}
	
	@RequestMapping( method = RequestMethod.POST, value="book/save")
	public ResponseDto saveBook(@RequestBody Book book, @RequestHeader("Authorization") String token) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		Optional<Book> existingBook = bookRepo.findByIsbn(book.getIsbn());
		if(!optionalUser.get().isAdmin()) {
			return new ResponseDto("Error 403: Forbidden");
		}
		
		if(existingBook.isPresent()) {
			return new ResponseDto("Book with this isbn number already present");
		}
		bookRepo.save(book);
		
		return new ResponseDto();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "book/update/{id}")
	public ResponseDto updateBookById(@PathVariable long id, @RequestBody Book book, @RequestHeader("Authorization") String token) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if(!optionalUser.get().isAdmin()) {
			return new ResponseDto("Error 403: Forbidden");
		}
		
		Optional<Book> optional = bookRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book does not exist yet.");
		}
		
		Book bookDb = optional.get();
		// If no title is provided it won't update the title.
				if(book.getTitle() == "") {
					bookDb.setTitle(bookDb.getTitle());			
				}
				else {
					bookDb.setTitle(book.getTitle());
				}
				// If no author is provided it won't update the author.
				if(book.getAuthor() == "") {
					bookDb.setAuthor(bookDb.getAuthor());
				}
				else {
					bookDb.setAuthor(book.getAuthor());
				}
				// If no ISBN is provided it won't update the ISBN.
				if(book.getIsbn()==""){
					bookDb.setIsbn(bookDb.getIsbn());
				}
				else {
					bookDb.setIsbn(book.getIsbn());
				}
				
		
		bookRepo.save(bookDb);
		
		return new ResponseDto();		
	}
	
	@DeleteMapping("book/delete/{id}")
	public ResponseDto deleteBookById(@PathVariable long id) {
		bookRepo.deleteById(id);
		return new ResponseDto();	
	}
	
	@GetMapping("book/{id}/copies")
	public Stream<ExtendedBookCopyDto> viewCopies(@PathVariable long id, @RequestHeader("Authorization") String token){
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if(!optionalUser.get().isAdmin()) {
			return null;
		}
		
		Optional<Book> optionalBook = bookRepo.findById(id);
		
		if (optionalBook.isEmpty()) {
			return null;
		}
		
		return optionalBook.get().getBookCopies().stream().map(b -> new ExtendedBookCopyDto(b));
	}

	@PostMapping("book/{id}/savecopy")
	public ResponseDto saveCopyOnBookId(@PathVariable long id, @RequestBody SaveBookCopiesDto dto, @RequestHeader("Authorization") String token) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if(!optionalUser.get().isAdmin()) {
			return new ResponseDto("Error 403: Forbidden");
		}
		Optional<Book> optionalBook = bookRepo.findById(id);
		if (optionalBook.isEmpty()) {
			return new ResponseDto("This book doesn't exist.");
		}

		int currentNoOfBooks = optionalBook.get().getBookCopies().size();
		for (int i = 0; i < dto.getAmount(); i++) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBook(optionalBook.get());
			bookCopy.setWtId(currentNoOfBooks + 1 + i);
			bookCopyRepo.save(bookCopy);
		}

		return new ResponseDto();
	}
	
	@RequestMapping("book/{id}/availablecopies")
	public Stream<AvailableBookCopyDto> viewAvailableCopies(
			@PathVariable long id,
			@RequestHeader("Authorization") String token) {
		Optional<Book> optionalBook = bookRepo.findById(id);
		if (optionalBook.isEmpty()) {
			return null;
		}
		
		Optional<User> optionalUser = userRepo.findByToken(token);
		if (optionalUser.isEmpty()) {
			return null;
		}
		
		if (optionalUser.get().isAdmin()) {
			List<BookCopy> availableCopies = new ArrayList<>();
			
			// Book -> Copieen hebben of ze uitgeleend zijn
			List<BookCopy> bookCopies = optionalBook.get().getBookCopies();

			for (BookCopy bookCopy : bookCopies) {
				long bookCopyCount = borrowedCopyRepo.countByBookCopy(bookCopy);
				
				if (bookCopyCount == 0 && bookCopy.getWtId()!= 0) {
					availableCopies.add(bookCopy);
				} else {
					if (!borrowedCopyRepo.existsByBookCopyAndEndDateIsNull(bookCopy) && bookCopy.getWtId()!= 0) {
						availableCopies.add(bookCopy);
					}
				}
			}
			
			return availableCopies.stream().map(b -> new AvailableBookCopyDto(b, true));
		}
		
		return null;
	}

	/**
	 * This endpoint obtains the size of the available/good copies and the total/good copies.
	 * @param id
	 * @param token
	 * @return
	 */
	
	@RequestMapping("books/availabletotalcopies")
	public Stream<AvailableBooksDto> showavailablecopies(
			@RequestHeader("Authorization") String token){
			Optional<User> optionalUser = userRepo.findByToken(token);
			if (optionalUser.isEmpty()) {
				return null;
			}
			List <Book> books = bookRepo.findAll();
				
		
			return books.stream().map(b -> new AvailableBooksDto(b,viewAvailableCopies(b.getId(),token).count(), bookCopyRepo.countByBookAndWtIdIsGreaterThan(b,0)));
	}
	
	/**
	 * Comment added by Omur
	 * This function makes a reservation for a User
	 */
	@PostMapping("book/makereservation")
	public ResponseDto makeReservation(
			@RequestBody MakeReservationDto makeReservation,
			@RequestHeader("Authorization") String token 
	) {
		// Find the user with the token
		Optional<User> optionalUser = userRepo.findByToken(token);
		if (optionalUser.isEmpty()) {
			return new ResponseDto("security");
		}

		Optional<Book> optionalBook = bookRepo.findById(makeReservation.getBookId());

		Reservation res = new Reservation();
		res.setBook(optionalBook.get());
		res.setUser(optionalUser.get());

		resRepo.save(res);
		
		return new ResponseDto();
	}
	
	@RequestMapping("book/{id}/totalcopies")
	public Stream<TotalBookCopiesDto> viewCopiesByBookId(
			@PathVariable long id,
			@RequestHeader("Authorization") String token) {
		Optional<Book> optionalBook = bookRepo.findById(id);
		if (optionalBook.isEmpty()) {
			return null;
		}
		
		Optional<User> optionalUser = userRepo.findByToken(token);
		if (optionalUser.isEmpty()) {
			return null;
		}
		
		if (optionalUser.get().isAdmin()) {
			
			// Book -> Copieen hebben of ze uitgeleend zijn
			List<BookCopy> bookCopies = bookCopyRepo.findByBookOrderByWtIdDesc(optionalBook.get());
			
			return bookCopies.stream().map(bookCopy -> {
				
				if (borrowedCopyRepo.existsByBookCopyAndEndDateIsNull(bookCopy) && bookCopy.getWtId()!= 0) {
					return new TotalBookCopiesDto(bookCopy,"Borrowed");
				}
				
				else if (bookCopy.getWtId() == 0) {
					return new TotalBookCopiesDto(bookCopy,"Archived");
				}
				
				else {
					return new TotalBookCopiesDto(bookCopy,"Available");
				}
			});
		
		}

		return null;
	}
	
	/*adds new bookcopies
	 * 
	 */
	@PostMapping("book/isbn/savecopy")
	public ResponseDto saveCopyOnBookId(@RequestBody BookCopyIsbnDto dto, @RequestHeader("Authorization") String token) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if(!optionalUser.get().isAdmin()) {
			return new ResponseDto("Error 403: Forbidden");
		}
		Optional<Book> optionalBook = bookRepo.findByIsbn(dto.getIsbn());
		if (optionalBook.isEmpty()) {
			return new ResponseDto("This book doesn't exist.");
		}

		int currentNoOfBooks = optionalBook.get().getBookCopies().size();
		for (int i = 0; i < dto.getAmount(); i++) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBook(optionalBook.get());
			bookCopy.setWtId(currentNoOfBooks + 1 + i);
			bookCopyRepo.save(bookCopy);
		}

		return new ResponseDto();
	}
	
	@GetMapping("/books/search/{keyword}")
	public Stream<BookAvailableDto> listResults(@PathVariable String keyword, @RequestHeader("Authorization") String token) {
		if (keyword != null) {
			List<Book> books = bookRepo.search(keyword);
			return books.stream().map(book -> {
				long noOfBookCopies = bookCopyRepo.countByBookAndWtIdIsGreaterThan(book, 0);
				long noOfNonAvailable = borrowedCopyRepo.countByBookCopyBookAndEndDateIsNull(book);
				
				if (noOfBookCopies - noOfNonAvailable == 0) {
					return new BookAvailableDto(book, false);
				}
				else {
					return new BookAvailableDto(book, true);
				}
			});	
		}
		List<Book> books = bookRepo.findAll();
		return books.stream().map(book -> {
			long noOfBookCopies = bookCopyRepo.countByBookAndWtIdIsGreaterThan(book, 0);
			long noOfNonAvailable = borrowedCopyRepo.countByBookCopyBookAndEndDateIsNull(book);
			
			if (noOfBookCopies - noOfNonAvailable == 0) {
				return new BookAvailableDto(book, false);
			}
			else {
				return new BookAvailableDto(book, true);
			}
		});	
	}
	
	@GetMapping("/books/search")
	public Stream<BookAvailableDto> listResults(@RequestHeader("Authorization") String token) {
		
		List<Book> books = bookRepo.findAll();
		return books.stream().map(book -> {
			long noOfBookCopies = bookCopyRepo.countByBookAndWtIdIsGreaterThan(book, 0);
			long noOfNonAvailable = borrowedCopyRepo.countByBookCopyBookAndEndDateIsNull(book);
			
			if (noOfBookCopies - noOfNonAvailable == 0) {
				return new BookAvailableDto(book, false);
			}
			else {
				return new BookAvailableDto(book, true);
			}
		});	
	}
}

