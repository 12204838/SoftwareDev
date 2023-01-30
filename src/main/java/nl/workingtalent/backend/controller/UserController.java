package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
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
	
	@RequestMapping( method = RequestMethod.POST, value="user/save")
	public ResponseDto saveUser(@RequestBody User user) {
		userRepo.save(user);
		
		return new ResponseDto();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/update/{id}")
	public ResponseDto updateUserById(@PathVariable long id, @RequestBody User user) {
		Optional<User> optional = userRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book does not exist yet.");
		}
		
		User userDb = optional.get();
		
		// als geen naam wordt meegegeven dan update hij niet.
		if(user.getName() == "") {
			userDb.setName(userDb.getName());			
		}
		else {
			userDb.setName(user.getName());
		}
		// als geen username wordt meegegeven dan update hij niet.
		if(user.getUsername() == "") {
			userDb.setUsername(userDb.getUsername());
		}
		else {
			userDb.setUsername(user.getUsername());
		}
		// als geen password wordt meegegeven dan update hij niet.
		if(user.getPassword()==""){
			userDb.setPassword(userDb.getPassword());
		}
		else {
			userDb.setPassword(user.getPassword());
		}
		// als geen EMAIL wordt meegegeven dan update hij niet.
		if(user.getEmail() == "") {
			userDb.setEmail(userDb.getEmail());
		}
		else {
			userDb.setEmail(user.getEmail());
		}
		userDb.setAdmin(user.isAdmin());
		userDb.setActive(user.isActive());


		
		userRepo.save(userDb);
		
		return new ResponseDto();		
	}
	
	@DeleteMapping("user/delete/{id}")
	public ResponseDto deleteUserById(@PathVariable long id) {
		
		if (userRepo.findById(id).isEmpty()) {
			return new ResponseDto("Id doesn't exist");
		}
		userRepo.deleteById(id);
		return new ResponseDto();	
	}
}
	
