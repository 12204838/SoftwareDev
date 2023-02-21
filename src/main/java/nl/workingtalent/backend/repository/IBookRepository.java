package nl.workingtalent.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.workingtalent.backend.entity.Book;

public interface IBookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByIsbn(String isbn);

	@Query("SELECT b FROM Book b WHERE b.title LIKE %?1%"
            + " OR b.author LIKE %?1%"
            + " OR b.isbn LIKE %?1%")
    public List<Book> search(String keyword);
}
