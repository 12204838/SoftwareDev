package nl.workingtalent.backend.dto;


import nl.workingtalent.backend.entity.Reservation;

public class ReservationDto {
	private long id;
	
	private long bookId;
	
	private long userId;
	
	private String bookTitle;
	
	private String nameEmployee;
	
	private boolean approved;
	
	public ReservationDto() {
		
	}

	public ReservationDto(long bookId, long userId, boolean approved) {
		super();
		this.bookId = bookId;
		this.userId = userId;
		this.approved = approved;
	}

	public ReservationDto(Reservation reservation) {
		super();
		this.id = reservation.getId();
		this.bookTitle = reservation.getBook().getTitle();
		this.nameEmployee = reservation.getUser().getName();
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

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
