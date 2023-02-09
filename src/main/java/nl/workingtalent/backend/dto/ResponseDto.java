package nl.workingtalent.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDto {

	protected boolean success;
	
	protected List<String> validationMessages = new ArrayList<>();
	
	public ResponseDto() {
		this.success = true;
	}
	
	public ResponseDto(String validationMessage) {
		this.success = false;
		this.validationMessages.add(validationMessage);
	}

	public ResponseDto(boolean success, List<String> validationMessages) {
		super();
		this.success = success;
		this.validationMessages = validationMessages;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getValidationMessages() {
		return validationMessages;
	}

	public void setValidationMessages(List<String> validationMessages) {
		this.validationMessages = validationMessages;
	}
	
	
	
}
