package nl.workingtalent.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.User;

public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findByToken(String token);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR u.id LIKE %?1%")
    public List<User> search(String keyword);
	
}
