package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;

@RestController
public class BookCopyController {
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
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
