package web2022;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;




public class ExamDbConnector {
	
	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/league_db";
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
		DB_PROP.setProperty("user"	, "root");
		DB_PROP.setProperty("password", "");
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
	public boolean examineUser(String username, String passwordHash) throws Exception {
		
		boolean response = false;
		
		// ** Question (a) **
		
		// TODO: Write your code here (examine if there is a row with the given data in the appropriate table)
		String FIND_USER = "SELECT * FROM USERS WHERE USERNAME = ? and PASSWORDHASH = ?";
		
		final PreparedStatement ps = conn.prepareStatement(FIND_USER);
		ps.setString(1, username);
		ps.setString(2, passwordHash);
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			response = true;
		}
		
		return response;
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
	public int recordData(Date date, int team1id, int team1score, int team2id, int team2score) throws Exception {
		
		int rowsAffected = -1; 
		
		// ** Question (b) **
		
		// TODO: Write your code here (throw Exception in case of an error or inappropriate data)
		String INSERT = "INSERT INTO `game_data` VALUES (null, ?, ?, ?, ?, ?)";
		final PreparedStatement ps = conn.prepareStatement(INSERT);
		
		ps.setDate(1, date );
		ps.setInt(2, team1id );
		ps.setInt(3, team1score );
		ps.setInt(4, team2id );
		ps.setInt(5, team2score );
		
		
		rowsAffected = ps.executeUpdate();
		
		
		return rowsAffected;
	}
	
	public List<Object> fetchGames() throws Exception{
		
		
		String QUERY = "SELECT g.game_date, t1.name, g.team_1_score, t2.name, g.team_2_score FROM game_data g, teams t1, teams t2 WHERE g.team_1_id = t1.id and g.team_2_id = t2.id";
		List<Object> result = new ArrayList<>();
		final Statement ps = conn.createStatement();
		final ResultSet rs = ps.executeQuery(QUERY);
		while(rs.next()) {
		Object [] res = new Object[5];
		Date d = null;
		d = rs.getDate("g.game_date");
		res[0] = new java.util.Date(d.getTime());
		res[1] = rs.getString("t1.name");
		res[2] = rs.getInt("g.team_1_score");
		res[3] = rs.getString("t2.name");
		res[4] = rs.getInt("g.team_2_score");
		result.add(res);
		}
		return result;
		
	}


	/** For testing purposes ...  */
	public static void main(String[] args) throws Exception {
		
		System.out.println(ExamDbConnector.class.getSimpleName());
		
		// Get DB connector instance
		ExamDbConnector dbConnector = new ExamDbConnector();
		
		try {
			dbConnector.openDbConnection();
		}
		catch(Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " unable to connect ";
			System.out.println(errMsg);
			t.printStackTrace();
		}
		System.out.println("DB connection opened");
		
		// Check method 1 (password: pa) - It should return true
		final String username = "ub";
		final String pass = Util.getHash256("pb");
		boolean response1 = dbConnector.examineUser(username, pass);
		System.out.println(" - Check Method 1 - Response: " + response1);
		
		// Check method 2 - Normally, it should return 1
		java.sql.Date date = new java.sql.Date(1000000L); // 1970-01-01
		int team1id = 3;
		int team1score = 1000; 
		int team2id = 2;
		int team2score = 80;
		int response2 = dbConnector.recordData(date, team1id, team1score, team2id, team2score);
		System.out.println(" - Check Method 2 - Response: " + response2);
		
		// You can add here more tests, if being necessary
				
		dbConnector.closeDbConnection();
		System.out.println("DB connection closed");

	}
	
}

