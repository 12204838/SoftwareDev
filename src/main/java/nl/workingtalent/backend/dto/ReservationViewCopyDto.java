package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Reservation;

public class ReservationViewCopyDto {
	
	private long bookId;
	
	private long userId;

	
	public ReservationViewCopyDto(Reservation r) {
		super();
		this.bookId = r.getBook().getId();
		this.userId = r.getUser().getId();
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
