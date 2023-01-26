package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.repository.IBookRepository;

@RestController
public class BookController {

	@Autowired
	private IBookRepository bookRepo;
	
	@RequestMapping("books")
	public Stream<BookDto> books() {
		List<Book> books = bookRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return books.stream().map(book -> new BookDto(book));
	}
	
	@RequestMapping( method = RequestMethod.POST, value="book/save")
	public boolean saveBook(@RequestBody Book book) {
		bookRepo.save(book);
		return true;
	
	}
}
