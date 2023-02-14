package nl.workingtalent.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Book;

public interface IBookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByIsbn(String isbn);

}
