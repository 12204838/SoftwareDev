package nl.workingtalent.backend.dto;

import java.time.LocalDate;

import nl.workingtalent.backend.entity.BorrowedCopy;

public class BorrowedCopyDto {
	
	private long id;
	
	private long wtId;

	private long bookCopyId;
	
	private long userId;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private long bookId;

	
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	private String bookTitle;
	public BorrowedCopyDto() {
		
	}
	
	public BorrowedCopyDto(BorrowedCopy borrowedCopy) {
		super();
		this.id = borrowedCopy.getId();
		this.startDate = borrowedCopy.getStartDate();
		this.endDate = borrowedCopy.getEndDate();
		//this.bookTitle = borrowedCopy.getBookcopy().getBook().getTitle();
		this.bookCopyId = borrowedCopy.getBookCopy().getId();
		this.userId = borrowedCopy.getUser().getId();
		this.wtId = borrowedCopy.getBookCopy().getWtId();
		this.bookId = borrowedCopy.getBookCopy().getBook().getId();
	}

	public long getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWtId() {
		return wtId;
	}

	public void setWtId(long wtId) {
		this.wtId = wtId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

}
