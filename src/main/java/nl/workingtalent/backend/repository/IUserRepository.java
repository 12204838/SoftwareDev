package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.User;

public interface IUserRepository extends JpaRepository<User, Long>{

}
