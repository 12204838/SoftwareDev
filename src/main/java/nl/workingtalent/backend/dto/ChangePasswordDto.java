package nl.workingtalent.backend.dto;

public class ChangePasswordDto {
	
	private String password;
	
	public ChangePasswordDto() {}

	public ChangePasswordDto(String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
