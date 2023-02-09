package nl.workingtalent.backend.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BorrowedCopy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private BookCopy bookCopy;
	
	@Column(nullable = false, length = 100)
	private LocalDate startDate;
	
	@Column(nullable = true, length = 100)
	private LocalDate endDate;
	
	@ManyToOne
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}
	
	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
