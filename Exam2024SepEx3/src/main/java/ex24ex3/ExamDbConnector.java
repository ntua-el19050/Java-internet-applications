package ex24ex3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.DatabaseMetaData;
import ex24ex3.UserInfo;

public class ExamDbConnector {
	
	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/txtdb";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;	
	
	// Update Constructor, if being necessary
	public ExamDbConnector() {
		
	}
	
	/** Opens a DB connection (connection is internally stored in a private variable) */
	public void openDbConnection() throws Exception {
		// DB Connection Properties
		final Properties DB_PROP = new Properties();
		DB_PROP.setProperty("user"	, DB_USERNAME);
		DB_PROP.setProperty("password", DB_PASSWORD);
		DB_PROP.setProperty("charSet", "UTF-8");
		
		// Ensure that the DB Connector (i.e., Java Class) is available in your CLASSPATH
		// Update if being necessary
		Class.forName("com.mysql.cj.jdbc.Driver");
				
		// Get DB Connection
		this.conn = DriverManager.getConnection(DB_URL, DB_PROP);
	}
	
	/** Closes the DB connection (if any - internally stored in a private variable) */
	public void closeDbConnection() throws Exception {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}
	
	
	/**
	 * Examines if the user is valid or not. 
	 * 
	 * @param username
	 * 		The username
	 * @param passwordHash
	 * 		The password hash using SHA-256 algorithm
	 * @return
	 * 		<code>True</code> if the user is valid, otherwise <code>False</code>
	 * @throws Exception
	 * 		In case of an error (e.g., cannot connect to DB)
	 */
	public UserInfo examineUser(String username, String passwordHash) throws Exception {
		
		// boolean response = false;
		
		// ** Question (a) **
		
		// TODO: Write your code here (examine if there is a row with the given data in the appropriate table)
		String FIND_USER = "SELECT ID, ROLE_INDEX FROM USERS WHERE USERNAME = ? and PASSWORD_HASH = ?";
		
		final PreparedStatement ps = conn.prepareStatement(FIND_USER);
		ps.setString(1, username);
		ps.setString(2, passwordHash);
		final ResultSet rs = ps.executeQuery();
	    
	    // Check if there is a result
	    if (rs.next()) {
	        int id = rs.getInt("ID");
	        int roleIndex = rs.getInt("ROLE_INDEX");
	        return new UserInfo(id, roleIndex);  // Return a UserInfo object with data
	    } else {
	        return null; // No user found
	    }
	}


	/**
	 * Stores the given data in the appropriate DB table. 
	 * 
	 * @param date
	 * 		The {@link Date} when the game took place
	 * 
	 * @param team1id
	 * 		Team 1 unique ID (foreign key)
	 * @param team1score
	 * 		Team 1 score (positive integer)
	 * @param team2id
	 * 		Team 2 unique ID (foreign key)
	 * @param team2score
	 * 		Team 2 score (positive integer)
	 * @return
	 *		The number of DB rows affected
	 * @throws Exception
	 * 		In case of an error (e.g., cannot connect to DB) or when the data are invalid (e.g., negative numbers)		
	 */
	public int recordData(String s,int charCount,int letters,int numbers,int words) throws Exception {
		
		int rowsAffected = -1; 
		
		// ** Question (b) **
		
		
		
		// TODO: Write your code here (throw Exception in case of an error or inappropriate data)
		String INSERT = "INSERT INTO sentences VALUES (null, ?, ?, ?, ?, ?)";
		final PreparedStatement ps = conn.prepareStatement(INSERT);
		
		ps.setString(1, s );
		ps.setInt(2, charCount );
		ps.setInt(3, letters );
		ps.setInt(4, numbers);
		ps.setInt(5, words);
		
		System.out.println(ps);
		rowsAffected = ps.executeUpdate();
		
		
		return rowsAffected;
	}
	
	public List<Object> fetchTasks(String characteristic, String operator) throws Exception{
		
		
		String QUERY = "SELECT * FROM sentences WHERE " + characteristic + operator;
		System.out.println(QUERY);
		List<Object> result = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(QUERY);
		final ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		Object [] res = new Object[4];
		res[0] = rs.getInt("ID");
		res[1] = rs.getString("TEXTSTR");
		res[2] = rs.getInt("CHARS");
		res[3] = rs.getInt("LETTERS");
		res[4] = rs.getInt("NUMBERS");
		res[5] = rs.getInt("WORDS");
		result.add(res);
		}
		
		return result;
		
	}


}

