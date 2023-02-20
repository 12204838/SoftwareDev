package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.BookCopy;

public class TotalBookCopiesDto {
	
	private long id;
	
	//private long bookId;	//Updated the Data Transfer Object to display (or take in) a bookId instead of the whole book.

	private String bookTitle;
	
	private String workingTalentId;
	
	private String status;
	

	private long timesBorrowed;
	
	public TotalBookCopiesDto(BookCopy bookCopy, String status) {
		super();
		this.id = bookCopy.getId();
		this.bookTitle = bookCopy.getBook().getTitle();
		this.workingTalentId = (bookCopy.getBook().getId()+ "." + bookCopy.getWtId());
		this.status = status;
		this.timesBorrowed = bookCopy.getBorrowedCopies().size();
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getWorkingTalentId() {
		return workingTalentId;
	}
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}

	public void setWorkingTalentId(String workingTalentId) {
		this.workingTalentId = workingTalentId;
	}


	public long getTimesBorrowed() {
		return timesBorrowed;
	}

	public void setTimesBorrowed(long timesBorrowed) {
		this.timesBorrowed = timesBorrowed;
	}

}
