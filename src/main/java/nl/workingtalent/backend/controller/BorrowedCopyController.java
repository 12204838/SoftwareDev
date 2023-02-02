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

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.BorrowedCopyDto;
import nl.workingtalent.backend.dto.ResponseDto;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.BorrowedCopy;
import nl.workingtalent.backend.entity.User;
import nl.workingtalent.backend.repository.IBookCopyRepository;
import nl.workingtalent.backend.repository.IBookRepository;
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;
import nl.workingtalent.backend.repository.IUserRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class BorrowedCopyController {
	
	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;
	
	@Autowired
	private IBookCopyRepository bookCopyRepo;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IBookRepository bookRepo;
	
	
	@RequestMapping("borrowedcopies")
	public Stream<BorrowedCopyDto> borrowedCopies() {
		List<BorrowedCopy> borrowedCopies = borrowedCopyRepo.findAll();
		
		// Zet lijst van Book om naar lijst bookdto
		return borrowedCopies.stream().map(borrowedCopy -> new BorrowedCopyDto(borrowedCopy));
	}
	
	@RequestMapping( method = RequestMethod.POST, value="borrowedcopy/save")
	public ResponseDto saveBorrowedCopy(@RequestBody BorrowedCopy borrowedCopy) {
		borrowedCopyRepo.save(borrowedCopy);
		
		return new ResponseDto();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "borrowedcopy/savebyid")
	public ResponseDto updateBorrowedCopyById( @RequestBody BorrowedCopyDto borrowedCopyDto) {
		Optional<BookCopy> optional = bookCopyRepo.findById(borrowedCopyDto.getBookCopyId());
		Optional<User> optionalUser = userRepo.findById(borrowedCopyDto.getUserId());
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book copy does not exist yet.");
		}
		
		if (optionalUser.isEmpty()) {
			return new ResponseDto("This user does not exist yet.");
		}
		
		BorrowedCopy borrowedCopy = new BorrowedCopy();
		
		borrowedCopy.setBookcopy(optional.get());
		borrowedCopy.setUser(optionalUser.get());
		borrowedCopy.setEndDate(borrowedCopyDto.getEndDate());
		borrowedCopy.setStartDate(borrowedCopyDto.getStartDate());
//		borrowedCopyDb.setUserId(borrowedCopy.getUserId());

		
		borrowedCopyRepo.save(borrowedCopy);
		
		return new ResponseDto();		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "borrowedcopy/update/{id}")
    public ResponseDto updateBorrowedCopyById(@PathVariable long id, @RequestBody BorrowedCopy borrowedCopy) {
        Optional<BorrowedCopy> optional = borrowedCopyRepo.findById(id);

        if (optional.isEmpty()) {
            return new ResponseDto("This book copy does not exist yet.");
        }

        BorrowedCopy borrowedCopyDb = optional.get();

        borrowedCopyDb.setEndDate(borrowedCopy.getEndDate());

        borrowedCopyRepo.save(borrowedCopyDb);

        return new ResponseDto();
    }
	
	@DeleteMapping("borrowedcopy/delete/{id}")
	public ResponseDto deleteBorrowedCopyById(@PathVariable long id) {
		
		if (borrowedCopyRepo.findById(id).isEmpty()) {
			return new ResponseDto("Id doesn't exist");
		}
		borrowedCopyRepo.deleteById(id);
		return new ResponseDto();	
	}
	

}
