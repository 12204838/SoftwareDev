package nl.workingtalent.backend.dto;

import java.time.LocalDate;

import nl.workingtalent.backend.entity.BorrowedCopy;

public class BorrowedCopyDto {
	
	private LocalDate startDate;
	
	private String bookTitle;

	public BorrowedCopyDto(BorrowedCopy borrowedCopy) {
		super();
		this.startDate = borrowedCopy.getStartDate();
		this.bookTitle = borrowedCopy.getBookcopy().getBook().getTitle();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	

}
