package nl.workingtalent.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.User;

public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findByToken(String token);
	
}
