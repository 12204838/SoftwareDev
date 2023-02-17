package nl.workingtalent.backend.dto;

import java.time.LocalDate;

import nl.workingtalent.backend.entity.BorrowedCopy;

public class BorrowedCopyDto {
	
	private long id;
	
	private String wtId;

	private long bookCopyId;
	
	private String name;
	
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
		this.bookTitle = borrowedCopy.getBookCopy().getBook().getTitle();
		this.bookCopyId = borrowedCopy.getBookCopy().getId();
		this.name = borrowedCopy.getUser().getName();
		this.wtId = borrowedCopy.getBookCopy().getBook().getId() + "." + borrowedCopy.getBookCopy().getWtId();
		this.bookId = borrowedCopy.getBookCopy().getBook().getId();
	}

	public long getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setWtId(String wtId) {
		this.wtId = wtId;
	}
	
	public String getWtId() {
		return wtId;
	}
	
	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

}
