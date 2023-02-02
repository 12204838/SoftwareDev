package nl.workingtalent.backend.dto;

public class ChooseBookDto {
	public long userId;
	
	public long bookId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

}
