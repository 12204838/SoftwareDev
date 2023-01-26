package nl.workingtalent.backend.dto;


import nl.workingtalent.backend.entity.Reservation;

public class ReservationDto {
	private long id;
	
	private String bookTitle;
	
	private String nameEmployee;

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
	
	
}
