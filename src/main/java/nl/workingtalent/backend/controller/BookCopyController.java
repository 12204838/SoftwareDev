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
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class BookCopyController {
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
	@Autowired
	private IBookRepository bookRepo;	//Added a bookrepository to the controller, to make it easy to look up books when applying CRUD on bookCopy.
	
	@RequestMapping("bookcopies")
	public Stream<BookCopyDto> bookCopies() {
		List<BookCopy> bookCopies = bookCopyRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return bookCopies.stream().map(bookCopy -> new BookCopyDto(bookCopy));
	}
	
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
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "bookcopy/update/{id}")
	public ResponseDto updateBookCopyById(@PathVariable long id, @RequestBody BookCopy bookCopy) {
		Optional<BookCopy> optional = bookCopyRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book copy does not exist yet.");
		}
		
		BookCopy bookCopyDb = optional.get();
		
		bookCopyDb.setBook(bookCopy.getBook());

		
		bookCopyRepo.save(bookCopyDb);
		
		return new ResponseDto();		
	}
	
	@DeleteMapping("bookcopy/delete/{id}")
	public ResponseDto deleteBookCopyById(@PathVariable long id) {
		
		if (bookCopyRepo.findById(id).isEmpty()) {
			return new ResponseDto("Id doesn't exist");
		}
		bookCopyRepo.deleteById(id);
		return new ResponseDto();	
	}
	

}
