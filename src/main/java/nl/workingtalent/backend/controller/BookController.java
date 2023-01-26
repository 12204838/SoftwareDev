package nl.workingtalent.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.repository.IBookRepository;

@RestController
public class BookController {

	@Autowired
	private IBookRepository bookRepo;
	
	@RequestMapping("books")
	public List<Book> books() {
		return bookRepo.findAll();
	}
	
}
