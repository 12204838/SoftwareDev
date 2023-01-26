package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.User;

public class BookCopyDto {
	
		private long id;
		
		private Book book;

		public BookCopyDto(BookCopy bookCopy) {
			super();
			this.id = id;
			this.book = book;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}
		


}
