package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Book;

public class BookDto {

	private long id;
	
	private String title;
	
	private String author;
	
	private String isbn;
	
	private int bookCopies;
	
	public BookDto() {
		
	}
	
	public BookDto(Book book) {
		super();
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.isbn = book.getIsbn();
		this.bookCopies = book.getBookCopies().size();
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

	public int getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(int bookCopies) {
		this.bookCopies = bookCopies;
	}
	
}
