package exam2022db;

import java.util.Date;

public class User {

	private final Integer id;
	private final String username;
	private final String passwordHash;
	private final Date dateCreated;
	private final Integer roleId;
	
	public User(String username, String passwordHash) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.id = null;
		this.dateCreated = null;
		this.roleId = null;
	}
	public User(Integer id, String username, String passwordHash, Date dateCreated, Integer roleId) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = dateCreated;
		this.roleId = roleId;
	}
	public Integer getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public Integer getRoleId() {
		return roleId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", dateCreated="
				+ dateCreated + ", roleId=" + roleId + "]";
	}
	
	
	
	
	
}
