package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.BookCopy;

public class ExtendedBookCopyDto {
	
		private long id;
		
		private String bookTitle;
		
		private String workingTalentId;
		
		private long timesBorrowed;
		
		public ExtendedBookCopyDto() {
			
		}
		
		public ExtendedBookCopyDto(BookCopy bookCopy) {
			super();
			this.id = bookCopy.getId();
			this.setBookTitle(bookCopy.getBook().getTitle());
			this.setWorkingTalentId(bookCopy.getBook().getId()+ "." + bookCopy.getWtId());
			this.timesBorrowed = bookCopy.getBorrowedCopies().size();
		}
		
		public String getWorkingTalentId() {
			return workingTalentId;
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
