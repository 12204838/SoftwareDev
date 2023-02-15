package nl.workingtalent.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.ChangePasswordDto;
import nl.workingtalent.backend.dto.LoginRequestDto;
import nl.workingtalent.backend.dto.LoginResponseDto;
import nl.workingtalent.backend.dto.ReservationApproveDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.dto.SetAdminDto;
import nl.workingtalent.backend.dto.UserDto;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.BorrowedCopy;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class UserController {
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;
	
	/**
	 * Comment added by Omur
	 * Finds all the users in the database.
	 */
	@RequestMapping("users")
	public Stream<UserDto> users(@RequestHeader("Authorization") String token ) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		if (optionalUser.isEmpty()) {
			return null;
		}
		
		if (optionalUser.get().isAdmin()) {
			List<User> users = userRepo.findAll();
			return users.stream().map(user -> new UserDto(user));
		}else {
			return null;
		}
		
		// Zet lijst van Book om naar lijst bookdto
	}
	
	/**
	 * Comment added by Omur
	 * This function saves the newly added user in the database.
	 */
	@RequestMapping( method = RequestMethod.POST, value="user/save")
	public ResponseDto saveUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
		Optional<User> loginUser = userRepo.findByToken(token);
		if (loginUser.get().isAdmin()) {
			userRepo.save(user);
			return new ResponseDto();
			
		}
		return new ResponseDto("Error 403: Forbidden");
	}
	
	/**
	 * Comment added by Omur
	 * This function updates an user by the user ID.
	 * The function updates only the fields that are filled.
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "user/update/{id}")
	public ResponseDto updateUserById(@PathVariable long id, @RequestBody User user, @RequestHeader("Authorization") String token ) {
		Optional<User> loginUser = userRepo.findByToken(token);
		
		if (loginUser.get().isAdmin()) {
			
			Optional<User> optional = userRepo.findById(id);
			
			if (optional.isEmpty()) {
				return new ResponseDto("This user does not exist yet.");
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
		return new ResponseDto("Error 403: Forbidden");
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/{id}/setadmin")
	public ResponseDto setAdminById(@PathVariable long id, @RequestBody SetAdminDto dto, @RequestHeader("Authorization") String token ) {
		Optional<User> loginUser = userRepo.findByToken(token);
		
		if (loginUser.get().isAdmin()) {
			
			Optional<User> optional = userRepo.findById(id);
			
			if (optional.isEmpty()) {
				return new ResponseDto("This user does not exist yet.");
			}
			User userDb = optional.get();
			
			userDb.setAdmin(dto.isAdmin());

			userRepo.save(userDb);
			
			return new ResponseDto();		
		}
		return new ResponseDto("Error 403: Forbidden");
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/{id}/deactivate")
	public ResponseDto deactivateById(@PathVariable long id, @RequestHeader("Authorization") String token ) {
		Optional<User> loginUser = userRepo.findByToken(token);
		
		if (loginUser.get().isAdmin()) {
			
			Optional<User> optional = userRepo.findById(id);
			
			if (optional.isEmpty()) {
				return new ResponseDto("This user does not exist yet.");
			}
			User userDb = optional.get();
			
			userDb.setName("Former employee");
			userDb.setEmail("");
			userDb.setPassword("");
			userDb.setAdmin(false);
			userDb.setActive(false);

			userRepo.save(userDb);
			
			return new ResponseDto();		
		}
		return new ResponseDto("Error 403: Forbidden");
	}
	/**
	 * Comment added by Omur
	 * This function deletes an user with the give ID.
	 * It also gives a message if the user does not exist.
	 */

	@DeleteMapping("user/delete/{id}")
	public ResponseDto deleteUserById(@PathVariable long id) {
		
		if (userRepo.findById(id).isEmpty()) {
			return new ResponseDto("Id doesn't exist");
		}
		userRepo.deleteById(id);
		return new ResponseDto();	
	}
	
	
	@PostMapping("user/{id}/assign")
	public ResponseDto approveReservation(@PathVariable long id, @RequestBody ReservationApproveDto dto) {
		Optional<BookCopy> bookCopyOptional = this.bookCopyRepo.findById(dto.getBookCopyId());
			// Book copy aanmaken
			Optional<User> userOptional = this.userRepo.findById(dto.getUserId());
			if (bookCopyOptional.isPresent() && userOptional.isPresent()) {
				BorrowedCopy borrowedCopy = new BorrowedCopy();
				borrowedCopy.setBookCopy(bookCopyOptional.get());
				borrowedCopy.setStartDate(LocalDate.now());
				borrowedCopy.setUser(userOptional.get());
				
				borrowedCopyRepo.save(borrowedCopy);
			}
		
		return new ResponseDto();
	}
	
	@PostMapping("/user/login")
	public ResponseDto login(@RequestBody LoginRequestDto dto) {

		Optional<User> userOptional = this.userRepo.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			
			// Token instellen voor user
			user.setToken(RandomStringUtils.random(100, true, true));
			
			// User opslaan
			userRepo.save(user);
			
			// Token sturen we naar de frontend
			return new LoginResponseDto(user.getToken(), user.isAdmin(),user.isActive());
		}
		
		return new ResponseDto("user.not.found");
	}
	
	@PutMapping("/user/changepassword")
	public ResponseDto changePassword(@RequestBody ChangePasswordDto dto, @RequestHeader("Authorization") String token) {

		Optional<User> userOptional = this.userRepo.findByToken(token);
		if (userOptional.isPresent()) {
			User userDb = userOptional.get();

			userDb.setPassword(dto.getPassword());
			userRepo.save(userDb);
			
			return new ResponseDto("Password updated");
		}
		
		return new ResponseDto("User doesn't exists");
		
	}
	
	@GetMapping("/user/checkpassword")
	public ChangePasswordDto checkPassword(@RequestHeader("Authorization") String token) {

		Optional<User> userOptional = this.userRepo.findByToken(token);
		if (userOptional.isPresent()) {
			User userDb = userOptional.get();

			return new ChangePasswordDto(userDb.getPassword());
		}
		
		return null;
		
	}
	
}
	
