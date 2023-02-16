package model;

public class Users {
	private int idUsers;
	private String email;
	private String password;
	private String username;
	
	
	public Users() {
		idUsers = -1;
		email = "";
		password = "";
		username = "";
	}

	public Users(int id, String email, String password, String username) {
		setId(id);
		setEmail(email);
		setPassword(password);
		setUsername(username);
	}		
	
	public int getID() {
		return idUsers;
	}

	public void setId(int id) {
		this.idUsers = id;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "Nome do Usuário: " + username + "   E-mail: " + email;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Users) obj).getID());
	}	
}