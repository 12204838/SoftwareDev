package nl.workingtalent.backend.dto;


public class SetAdminDto {
	
	private boolean admin;

	
	public SetAdminDto() {
	}

	public SetAdminDto(boolean admin) {
		super();
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
