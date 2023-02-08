package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.BookCopy;

public class AvailableBookCopyDto {
	
		private long id;
		
		//private long bookId;	//Updated the Data Transfer Object to display (or take in) a bookId instead of the whole book.

		private String bookTitle;
		
		private String workingTalentId;
		
		private boolean available;
		
		private long timesBorrowed;
		
		public AvailableBookCopyDto(BookCopy bookCopy, boolean available) {
			super();
			this.id = bookCopy.getId();
			this.setBookTitle(bookCopy.getBook().getTitle());
			this.setWorkingTalentId(bookCopy.getBook().getId()+ "." + bookCopy.getWtId());
			this.timesBorrowed = bookCopy.getBorrowedCopies().size();
			this.setAvailable(available);
		}
		
		public String getWorkingTalentId() {
			return workingTalentId;
		}

		public void setWorkingTalentId(String workingTalentId) {
			this.workingTalentId = workingTalentId;
		}

		public boolean isAvailable() {
			return available;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}

		public long getTimesBorrowed() {
			return timesBorrowed;
		}

		public void setTimesBorrowed(long timesBorrowed) {
			this.timesBorrowed = timesBorrowed;
		}


		public AvailableBookCopyDto() {
			
		}
		
		/**
		 * This method obtains the Id from the object bookCopy and the id from the object Book
		 * 
		 * @param The object bookCopy
		 * @return The bookCopy id and the bookID from the book that belongs to the cookCopy
		 */	
//		public ExtendedBookCopyDto2(BookCopy bookCopy) {
//			super();
//			this.id = bookCopy.getId();				// Getting the ID from the bookCopy
//			this.bookId = bookCopy.getBook().getId(); 	// Getting the bookId from the book that belongs to the bookCopy.
//			this.setBookTitle(bookCopy.getBook().getTitle());
//		}

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
		
}
