package examdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class DbConn {

	// TODO: Check and Update URL if being necessary
	
	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/exam23sdb";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;	
	
	// Singleton Design Pattern
	private static DbConn instance = null;
		
	/** Ensure that we will create only one instance of this class */
	public static DbConn getInstance() {
		synchronized (DbConn.class) {
			if (instance == null) {
				instance  = new DbConn();
			}
			return instance;
		}
	}
	
	// Update Constructor, if being necessary
	private DbConn() {
		
	}
	
	/** Open DB Connection*/
	public void openDbConnection() throws SQLException, ClassNotFoundException {
		// DB Connection Properties
		final Properties DB_PROP = new Properties();
		DB_PROP.setProperty("user"	, DB_USERNAME);
		DB_PROP.setProperty("password", DB_PASSWORD);
		DB_PROP.setProperty("charSet", "UTF-8");
		
		// Ensure that the DB Connector (i.e., Java Class) is available in your CLASSPATH
		Class.forName("com.mysql.cj.jdbc.Driver");
				
		// Get DB Connection
		this.conn = DriverManager.getConnection(DB_URL, DB_PROP);
	}
	
	/** Close DB Connection */
	public void closeDbConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}
	
	
	private static final String SELECT_SQL_QUERY = 
		"SELECT COURSE_ID, COURSE_NAME, COURSE_SEMESTER, STUDENT_ID, STUDENT_GRADE " + 
		"FROM STUDENT_COURSE_DATA, COURSE_DATA WHERE COURSE_ID = COURSE_DATA.ID and COURSE_TEACHER_ID IN " + 
		" ( SELECT ID FROM USER_DATA WHERE USERNAME = ? AND PASSWORD = ? AND ROLE_ID = 'T' ) " + 
		"ORDER BY COURSE_SEMESTER";

	// TODO: Update Method (including its interface)
	public List<Object> getTeacherCoursesStudents(String username, String password) throws Exception {
		
		List<Object> result = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(SELECT_SQL_QUERY);
		ps.setString(1, username);
		ps.setString(2, password);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Object [] res = new Object[5];
			res[0] = rs.getInt("COURSE_ID");
			res[1] = rs.getString("COURSE_NAME");
			res[2] = rs.getInt("COURSE_SEMESTER");
			res[3] = rs.getInt("STUDENT_ID");
			res[4] = rs.getInt("STUDENT_GRADE");
			result.add(res);
		}
		return result;
	}

	
	private static final String UPDATE_SQL_QUERY =
		"UPDATE STUDENT_COURSE_DATA SET STUDENT_GRADE = ? " + 
		"WHERE STUDENT_ID = ? AND COURSE_ID = ?";
	
	// TODO: Update Method 
	public boolean updateStudentCourseData(Integer studentId, Integer courseId, Integer grade) throws Exception {
		
		boolean result = false;
		int row = -1;
		final PreparedStatement ps = conn.prepareStatement(UPDATE_SQL_QUERY);
		ps.setInt(1,grade );
		ps.setInt(2,studentId );
		ps.setInt(3,courseId );
		row = ps.executeUpdate();
		if(row>0) {
			result=true;
		}
		return result;
	}
	
	
	/**
	 * For Testing Purposes ...
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("Open DB connection ...\n");
		DbConn.getInstance().openDbConnection();
		
		// Question 2.3 (a) tests
		
		final String username = "tvar";
		final String password = "tv@r";
		List<Object> objList = DbConn.getInstance().getTeacherCoursesStudents(username, password);
		if (objList != null) {
			for (int i =0; i< objList.size();i++) {
				Object [] obj =  (Object[]) objList.get(i);
				System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3] + " "+ obj[4]);
			}
		}
		
		// Question 2.3 (b) tests
		
		boolean update1response = DbConn.getInstance().updateStudentCourseData(9, 9, 10);
		System.out.println("\nStudent:9 , Course:9 - Update Response: " + update1response + " (correct false)");
		
		boolean update2response = DbConn.getInstance().updateStudentCourseData(3, 1, 10);
		System.out.println("\nStudent:3 , Course:1 - Update Response: " + update2response + " (correct true)");
		
		System.out.println("\nClose DB connection ... ");
		DbConn.getInstance().closeDbConnection();
		
	}

}

