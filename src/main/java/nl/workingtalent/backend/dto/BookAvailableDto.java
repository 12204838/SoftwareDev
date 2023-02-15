package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Book;

public class BookAvailableDto extends BookDto {
		private boolean available;
		
		public BookAvailableDto(Book book,boolean available) {
			super(book);
			this.available = available;
		}

		public boolean isAvailable() {
			return available;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}
		
		
}
