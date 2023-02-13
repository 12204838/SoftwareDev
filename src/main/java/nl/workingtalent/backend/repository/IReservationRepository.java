package nl.workingtalent.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.entity.User;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findByUser(User user);

}
