package nl.workingtalent.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long>{
	
	long countByBookAndWtIdIsGreaterThan(Book book,long threshold);

}
