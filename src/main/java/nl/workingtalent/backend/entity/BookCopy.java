package nl.workingtalent.backend.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BookCopy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne()
	private Book book;
	
	private long wtId;
	
	@OneToMany(mappedBy = "bookCopy")
	private List<BorrowedCopy> borrowedCopies;

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

	public List<BorrowedCopy> getBorrowedCopies() {
		return borrowedCopies;
	}

	public void setBorrowedCopies(List<BorrowedCopy> borrowedCopies) {
		this.borrowedCopies = borrowedCopies;
	}

	public long getWtId() {
		return wtId;
	}

	public void setWtId(long wtId) {
		this.wtId = wtId;
	}
	
	
	
}
