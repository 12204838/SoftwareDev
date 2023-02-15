package nl.workingtalent.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.BorrowedCopy;
import nl.workingtalent.backend.entity.User;

public interface IBorrowedCopyRepository extends JpaRepository<BorrowedCopy, Long>{
	
//	 @Query("SELECT b FROM BorrowedCopy b WHERE b.bookcopy.id=?1 AND b.endDate IS NULL")
//	 Collection<BorrowedCopy> findByBookCopyIdAndEndDateIsNull(long bookCopyId);
	
	// Derived queries
	// https://www.baeldung.com/spring-data-derived-queries

	List<BorrowedCopy> findByBookCopyAndEndDateIsNull(BookCopy bookCopy);
	
	boolean existsByBookCopyAndEndDateIsNull(BookCopy bookCopy);
	
	long countByBookCopy(BookCopy bookCopy);
	
	List<BorrowedCopy> findByUser(User user);
	
	List<BorrowedCopy> findByStartDate(LocalDate startDate);
	
	List<BorrowedCopy> findByBookCopyBookAndEndDateIsNull(Book book);
	
	long countByBookCopyBookAndEndDateIsNull(Book book);
	
	List<BorrowedCopy> findByEndDateIsNull();
	 
}
