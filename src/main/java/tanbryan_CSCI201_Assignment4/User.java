package tanbryan_CSCI201_Assignment4;


public class User {
	private String username, email, password;
	private int userID;
	
	public User(String username, String email, String password, int userID) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
