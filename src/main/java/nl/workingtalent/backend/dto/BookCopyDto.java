package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.BookCopy;

public class BookCopyDto {
	
		private long id;
		
		private long bookId;	//Updated the Data Transfer Object to display (or take in) a bookId instead of the whole book.

		public BookCopyDto() {
			
		}
		
		public BookCopyDto(BookCopy bookCopy) {
			super();
			this.id = bookCopy.getId();
			this.bookId = bookCopy.getBook().getId(); 	// Getting the bookId from the book that belongs to the bookCopy.
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getBookId() {
			return bookId;
		}

		public void setBookId(long bookId) {
			this.bookId = bookId;
		}
		
}
