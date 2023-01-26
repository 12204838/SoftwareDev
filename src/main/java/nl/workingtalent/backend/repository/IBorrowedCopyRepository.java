package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.BorrowedCopy;

public interface IBorrowedCopyRepository extends JpaRepository<BorrowedCopy, Long>{

}
