package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IUserRepository;

@RestController
public class UserController {
	
	@Autowired
	private IUserRepository userRepo;
	
	@RequestMapping("users")
	public Stream<UserDto> users() {
		List<User> users = userRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return users.stream().map(user -> new UserDto(user));
	}
	

}
