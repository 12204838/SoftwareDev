package nl.workingtalent.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
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
import nl.workingtalent.backend.repository.IBorrowedCopyRepository;

@RestController
public class BorrowedCopyController {
	
	@Autowired
	private IBorrowedCopyRepository borrowedCopyRepo;
	
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
	
	@RequestMapping(method = RequestMethod.PUT, value = "borrowedcopy/update/{id}")
	public ResponseDto updateBorrowedCopyById(@PathVariable long id, @RequestBody BorrowedCopy borrowedCopy) {
		Optional<BorrowedCopy> optional = borrowedCopyRepo.findById(id);
		
		if (optional.isEmpty()) {
			return new ResponseDto("This book copy does not exist yet.");
		}
		
		BorrowedCopy borrowedCopyDb = optional.get();
		
		borrowedCopyDb.setBookcopy(borrowedCopy.getBookcopy());
		borrowedCopyDb.setEndDate(borrowedCopy.getEndDate());
		borrowedCopyDb.setStartDate(borrowedCopy.getStartDate());
		borrowedCopyDb.setUserId(borrowedCopy.getUserId());

		
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
