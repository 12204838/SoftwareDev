package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.BookCopyIsbnDto;
import nl.workingtalent.backend.dto.BorrowedCopyDto;
import nl.workingtalent.backend.dto.ExtendedBookCopyDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.BorrowedCopy;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class BookCopyController {
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
	@Autowired 
	private IUserRepository userRepo;
	
	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;	//Ad
	
	@Autowired
	private IBookRepository bookRepo;	//Added a bookrepository to the controller, to make it easy to look up books when applying CRUD on bookCopy.
	
	@RequestMapping("bookcopies")
	public Stream<ExtendedBookCopyDto> bookCopies(
			@RequestHeader("Authorization") String token ) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		List<BookCopy> bookCopies = bookCopyRepo.findAll();
		 if (optionalUser.get().isAdmin()) {
			 return bookCopies.stream().map(bookCopy -> new ExtendedBookCopyDto(bookCopy));
		 }
		 else {
			 return null;
		 }
			 

		
	}
	
	/**
	 * This method saves a bookCopy based on the input of the entire book class.
	 * 
	 * @param bookCopy the class which contains the book object and bookcopyId.
	 * @return a Response Data Transfer Object to show if everything worked as intended. 
	 */
	
	@RequestMapping( method = RequestMethod.POST, value="bookcopy/save")
	public ResponseDto saveBookCopy(@RequestBody BookCopy bookCopy) {
		bookCopyRepo.save(bookCopy);
		
		return new ResponseDto();
	}
	
	/**
	 * This method saves a bookCopy based on the id of the book.
	 * 
	 * @param BookCopyDto the Data Transfer Object that represents the book which contains the bookId
	 * @return a Response Data Transfer Object to show if everything worked as intended 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "bookcopy/savebybookid")
	public ResponseDto saveBookCopyByBookId(@RequestBody BookCopyDto bookCopyDto) {
		Optional<Book> optionalBook = bookRepo.findById(bookCopyDto.getBookId());
		
		if (optionalBook.isEmpty()) {
			return new ResponseDto("The book of which you try to add copies does not exist.");
		}
		
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(optionalBook.get());
		
		bookCopyRepo.save(bookCopy);
		
		return new ResponseDto();
	}
	
	/**
	 * This method updates a bookCopy based on the id of the bookCopy.
	 * 
	 * @param The bookCopy id and the object book
	 * @return a Response Data Transfer Object to show if everything worked as intended 
	 */
	
	@RequestMapping(method = RequestMethod.PUT, value = "bookcopy/update/{id}")
	public ResponseDto updateBookCopyById(@PathVariable long id, @RequestHeader("Authorization") String token) {
		Optional<BookCopy> optional = bookCopyRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByToken(token);
		
		if(!optionalUser.get().isAdmin()) {
			return new ResponseDto("Error 403: Forbidden");
		}
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book copy does not exist yet.");
		}
		
		BookCopy bookCopyDb = optional.get();
		
		bookCopyDb.setWtId(0);
		
		bookCopyRepo.save(bookCopyDb);
		
		return new ResponseDto();		
	}
	
	/**
	 * This method deletes a bookCopy based on the id of the bookCopy.
	 * 
	 * @param The Id of the bookCopy
	 * @return a Response Data Transfer Object to show if everything worked as intended 
	 */
	
	
	@DeleteMapping("bookcopy/delete/{id}")
	public ResponseDto deleteBookCopyById(@PathVariable long id) {
		
		if (bookCopyRepo.findById(id).isEmpty()) {
			return new ResponseDto("Id doesn't exist");
		}
		bookCopyRepo.deleteById(id);
		return new ResponseDto();	
	}
	
	
	@RequestMapping("bookcopy/{id}/history")
	public Stream<BorrowedCopyDto> CopyHistory(@PathVariable long id,
			@RequestHeader("Authorization") String token ) {
		Optional <BookCopy> optionalBookCopy = bookCopyRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByToken(token);
		List<BorrowedCopy> borrowedCopyHistory = borrowedCopyRepo.findByBookCopy(optionalBookCopy.get());
		 if (optionalUser.get().isAdmin()) {
			 return borrowedCopyHistory.stream().map(borrowedCopy -> new BorrowedCopyDto(borrowedCopy));
		 }
		 else {
			 return null;
		 }			 
		
	}


}
