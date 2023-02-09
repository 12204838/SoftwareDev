package nl.workingtalent.backend.dto;

public class LoginResponseDto extends ResponseDto {

	private String token;
	
	public LoginResponseDto(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
