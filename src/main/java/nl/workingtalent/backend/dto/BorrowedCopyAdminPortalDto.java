package nl.workingtalent.backend.dto;

import java.time.LocalDate;

import nl.workingtalent.backend.entity.BorrowedCopy;

public class BorrowedCopyAdminPortalDto {
	
	private long id;
	
	private String title;
	
	private String name;
	
	private String wtId;
	
	private LocalDate startDate;

	public BorrowedCopyAdminPortalDto() {
		
	}
	
	public BorrowedCopyAdminPortalDto(BorrowedCopy brc) {
		this.id = brc.getId();
		this.title = brc.getBookCopy().getBook().getTitle();
		this.name = brc.getUser().getName();
		this.wtId = brc.getBookCopy().getBook().getId() + "." + brc.getBookCopy().getWtId();
		this.startDate = brc.getStartDate();		
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWtId() {
		return wtId;
	}

	public void setWtId(String wtId) {
		this.wtId = wtId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}	
}
