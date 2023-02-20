package nl.workingtalent.backend.dto;

public class BookCopyIsbnDto {
	
	private String isbn;
	
	private long amount;
	
	

	public BookCopyIsbnDto(String isbn, long amount) {
		super();
		this.isbn = isbn;
		this.amount = amount;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	

}
