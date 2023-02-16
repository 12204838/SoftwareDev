package nl.workingtalent.backend.dto;

import java.time.LocalDate;

public class BorrowedCopyAdminPortalDto {
	
	private String title;
	
	private String name;
	
	private String wtId;
	
	private LocalDate startDate;

	public BorrowedCopyAdminPortalDto() {
		super();
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
