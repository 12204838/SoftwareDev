package nl.workingtalent.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.User;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long>{
	
	long countByBookAndWtIdIsGreaterThan(Book book,long threshold);
	
	List<BookCopy> findByBookAndWtIdIsGreaterThan(Book book, long threshold);
	
	List<BookCopy> findByBookOrderByWtIdDesc(Book book);
	


}
