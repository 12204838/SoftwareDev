package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Book;

public class AvailableBooksDto {
	
	private long id;
	
	private String title;
	
	private String author;
	
	private String isbn;
	
	private long availableBookCopies;
	
	private long totalBookCopies;
	
	public AvailableBooksDto() {
		
	}
	public AvailableBooksDto(Book book,long availableBookCopies,long totalBookCopies) {
		super();
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.isbn = book.getIsbn();
		this.availableBookCopies = availableBookCopies;
		this.totalBookCopies = totalBookCopies;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public long getAvailableBookCopies() {
		return availableBookCopies;
	}
	public void setAvailableBookCopies(int availableBookCopies) {
		this.availableBookCopies = availableBookCopies;
	}
	public long getTotalBookCopies() {
		return totalBookCopies;
	}
	public void setTotalBookCopies(int totalBookCopies) {
		this.totalBookCopies = totalBookCopies;
	}


}
