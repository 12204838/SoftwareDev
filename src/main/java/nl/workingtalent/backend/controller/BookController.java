package nl.workingtalent.backend.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.ExtendedBookCopyDto;
import nl.workingtalent.backend.dto.MakeReservationDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;
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
	private IReservationRepository resRepo;
	
	@RequestMapping("books")
	public Stream<BookDto> books() {
		List<Book> books = bookRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return books.stream().map(book -> new BookDto(book));
	}
	
	@RequestMapping( method = RequestMethod.POST, value="book/save")
	public ResponseDto saveBook(@RequestBody Book book) {
		bookRepo.save(book);
		
		return new ResponseDto();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "book/update/{id}")
	public ResponseDto updateBookById(@PathVariable long id, @RequestBody Book book) {
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
	public Stream<ExtendedBookCopyDto> viewCopies(@PathVariable long id){
		Optional<Book> optionalBook = bookRepo.findById(id);
		
		if (optionalBook.isEmpty()) {
			return null;
		}
		
		return optionalBook.get().getBookCopies().stream().map(b -> new ExtendedBookCopyDto(b));
	}
	
	@PostMapping("book/{id}/savecopy")
	public ResponseDto saveCopyOnBookId(@PathVariable long id) {
		Optional<Book> optionalBook = bookRepo.findById(id);
		
		if (optionalBook.isEmpty()) {
			return new ResponseDto("This book doesn't exist.");
		}
		
		int currentNoOfBooks = optionalBook.get().getBookCopies().size();
		
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(optionalBook.get());
		bookCopy.setWtId(currentNoOfBooks + 1); 
		
		bookCopyRepo.save(bookCopy);
		
		return new ResponseDto();
	}
	
	/**
	 * Comment added by Omur
	 * This function makes a reservation for a User
	 */
	@PostMapping("book/makereservation")
	public ResponseDto makeReservation(@RequestBody MakeReservationDto makeReservation) {
		Optional<Book> optionalBook = bookRepo.findById(makeReservation.getBookId());
		Optional<User> optionalUser = userRepo.findById(makeReservation.getUserId());
		
		Reservation res = new Reservation();
		res.setBook(optionalBook.get());
		res.setUser(optionalUser.get());
		
		resRepo.save(res);
		
		
		return new ResponseDto();
		
	}
}
