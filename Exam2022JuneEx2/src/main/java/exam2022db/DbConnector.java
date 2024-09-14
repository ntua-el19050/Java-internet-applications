package exam2022db;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;





public class DbConnector {

	
	// DB URL with HOST IP, PORT and DB NAME
			private static final String DB_URL = "jdbc:mysql://localhost:3306/internet";
			// DB credentials
			private static final String DB_USERNAME = "root";
			private static final String DB_PASSWORD = "";
			
			// DB Connection - Used by the other methods of this class
			private Connection conn;
			
			// Singleton Design Pattern
			private static DbConnector instance = null;
			/** Ensure that we will create only one instance of this class */
			public static DbConnector getInstance() {
				synchronized (DbConnector.class) {
					if (instance == null) {
						instance  = new DbConnector();
					}
					return instance;
				}
			}
			private DbConnector() {
				
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
			
			
			public void closeDbConnection() throws SQLException {
				if (conn != null && !conn.isClosed()) {
					this.conn.close();
				}
			}
			
			private static final String FIND_USER = 
					"SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD_HASH=?";

			public User findUser(String username, String passwordHash) throws SQLException {
				User user = null;
				final PreparedStatement ps = conn.prepareStatement(FIND_USER);
				ps.setString(1, username);
				ps.setString(2, passwordHash);
				final ResultSet rs = ps.executeQuery();
				while(rs.next()) {
				int id = rs.getInt("ID");
				int role_id = rs.getInt("ROLE_ID");
				Date date = rs.getDate("DATE_CREATED");
				user = new User(id,username,passwordHash,date,role_id);
				break;
				}
				return user;
			
			}
			
			
			private static final String UPDATE_USER = 
					"UPDATE USERS SET PASSWORD_HASH = ? WHERE USERNAME = ?";
			
			public int  UpdateUser(String username, String passwordHash) throws SQLException {
				int rs;
				final PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
				ps.setString(1, passwordHash );
				ps.setString(2,username );
				rs = ps.executeUpdate();
				return rs;
			
			}
	
			
			private static final String ADMIN_QUERY = 
					"SELECT dm.NAME, count(*) FROM CASES c, DIAGNOSIS_METHODS dm WHERE c.DIAGNOSIS_METHOD_ID = dm.ID GROUP BY dm.NAME";
					

			public List<Object> adminCase() throws SQLException {
				
				List<Object> result = new ArrayList<>();
				final Statement ps = conn.createStatement();
				final ResultSet rs = ps.executeQuery(ADMIN_QUERY);
				while(rs.next()) {
				Object [] res = new Object[2];
				res[0] = rs.getString("dm.NAME");
				res[1] = rs.getInt(2);
				result.add(res);
				}
				return result;
			
			}
			
			
			private static final String USER_CASE = 
					"SELECT * FROM CASES c, DIAGNOSIS_METHODS dm WHERE c.DIAGNOSIS_METHOD_ID = dm.ID AND c.USER_ID = ?";
					

			public List<Cases> userCase(int userid) throws SQLException {
				List<Cases> result = new ArrayList<>();
				final PreparedStatement ps = conn.prepareStatement(USER_CASE);
				ps.setInt(1, userid);
				final ResultSet rs = ps.executeQuery();
				while(rs.next()) {
				int id = rs.getInt("ID");
				int uid = rs.getInt("USER_ID");
				int mid = rs.getInt("DIAGNOSIS_METHOD_ID");
				Date date = rs.getDate("DIAGNOSIS_DATE");
				String dl = rs.getString("DIAGNOSIS_LOCATION");
				String rid = rs.getString("DIAGNOSIS_REPORT_UID");
				String name = rs.getString("NAME");
				
				
				Cases c = new Cases(id,uid,date,mid,dl,rid,name);
				result.add(c);
				}
				
				return result;
			
			}
			
			private static final String INSERT_CASE = 
					"INSERT INTO CASES VALUES (null, ?, ?, ?, ?, ?)";
			
			public int  insertCase(int uid, java.util.Date d, int mid,String loc, String rid) throws SQLException {
				int rs;
				final PreparedStatement ps = conn.prepareStatement(INSERT_CASE);
				ps.setInt(1, uid );
				java.sql.Date sqlDate = new java.sql.Date(d.getTime());
				ps.setDate(2, sqlDate);
				ps.setInt(3,mid);
				ps.setString(4,loc);
				ps.setString(5,rid);
				rs = ps.executeUpdate();
				return rs;
			
			}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" >> ProgramDB - Testing Place - START");
		System.out.println();
		
		final DbConnector db = DbConnector.getInstance();
		db.openDbConnection();
		Date sqlDate =null;
		final String dateFormat = "yyyy/MM/dd";
		final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			 java.util.Date date = sdf.parse("2022/10/12");
			  sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// View Data
		int result = db.insertCase(1,sqlDate,2,"Athens",null);
		
		
		System.out.println(result);
		
		db.closeDbConnection();
		
		System.out.println();
		System.out.println(" >> ProgramDB - Testing Place - END");
	}

}
