package nl.workingtalent.backend.dto;

public class LoginResponseDto extends ResponseDto {

	private String token;
	
	private boolean admin;
	
	private boolean active;

	public LoginResponseDto(String token, boolean admin, boolean active) {
		this.token = token;
		this.admin = admin;
		this.active = active;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
