package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.User;

public class UserDto {
	
		private long id;
		
		private String name;
		
		private String email;
		
		private boolean admin;
		
		private boolean active;

		public UserDto(User user) {
			super();
			this.id = user.getId();
			this.name = user.getName();
			this.email = user.getEmail();
			this.admin = user.isAdmin();
			this.active = user.isActive();
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean isAdmin() {
			return admin;
		}

		public void setAdmin(boolean admin) {
			this.admin = admin;
		}
		
		public void setActive(boolean active) {
			this.active = active;
		}
		
		public boolean isActive() {
			return active;
		}
			
			
}
