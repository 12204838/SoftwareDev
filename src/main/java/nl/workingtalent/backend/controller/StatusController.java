package nl.workingtalent.backend.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class StatusController {

	@RequestMapping("status")
	public String status() {
		return "OK: " + LocalDateTime.now();
	}
	
}
