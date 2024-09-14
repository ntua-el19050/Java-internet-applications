package exam2023db;

public class User {

	private final Integer id;
	private final String username;
	private final String password;
	private final Integer roleId;
	
	public User(String username, String password) {
		this.id = null;
		this.username = username;
		this.password = password;
		this.roleId = null;
	}

	public User(Integer id, String username, String password, Integer roleId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	
	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getRoleId() {
		return roleId;
	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "roleID = " + roleId+ " ]";
	}

	

}
