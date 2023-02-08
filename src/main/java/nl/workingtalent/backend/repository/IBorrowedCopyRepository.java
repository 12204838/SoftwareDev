package nl.workingtalent.backend.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.workingtalent.backend.entity.BorrowedCopy;

public interface IBorrowedCopyRepository extends JpaRepository<BorrowedCopy, Long>{
	
	 @Query("SELECT b FROM BorrowedCopy b WHERE b.bookcopy.id=?1 AND b.endDate IS NULL")
	 Collection<BorrowedCopy> findByBookCopyIdAndEndDateIsNull(long bookCopyId);
	 
}
