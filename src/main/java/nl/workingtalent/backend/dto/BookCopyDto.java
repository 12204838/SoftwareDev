package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.BookCopy;

public class BookCopyDto {
	
		private long id;
		
		private long bookId;	//Updated the Data Transfer Object to display (or take in) a bookId instead of the whole book.

		
		public BookCopyDto() {
			
		}
		
		/**
		 * This method obtains the Id from the object bookCopy and the id from the object Book
		 * 
		 * @param The object bookCopy
		 * @return The bookCopy id and the bookID from the book that belongs to the cookCopy
		 */	
		public BookCopyDto(BookCopy bookCopy) {
			super();
			this.id = bookCopy.getId();				// Getting the ID from the bookCopy
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
