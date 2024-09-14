package exam2023db;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class DbConnector {

	
	// DB URL with HOST IP, PORT and DB NAME
		private static final String DB_URL = "jdbc:mysql://localhost:3306/EXAM2306DB";
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
		
		
		private static final String SELECT_USER_SQL_QUERY = 
				"SELECT * FROM USER , ROLE WHERE USER.ROLE_ID=ROLE.ID AND USERNAME=? AND USERPASS=?";
			
			public Object[] getRole(final String username, final String password) throws SQLException {
				Object [] result = new Object[2];
				Role role = null;
				int id = 0;
				final PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL_QUERY);
				ps.setString(1, username);
				ps.setString(2, password);
				final ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					id = rs.getInt("ID");
					final int rid = rs.getInt("ROLE_ID");
					final String name = rs.getString("NAME");
					role = new Role(rid,name);
					break;
				}
				result[0]= role;
				result[1]= id;
				rs.close();
				ps.close();
				return result;
			}
			
			
			
			private static final String SELECT_TASK_SQL_QUERY = 
					"SELECT * FROM TASK , STATUS WHERE TASK.STATUS_ID = STATUS.ID AND USER_ID = ?";
				
				public Object[] getTask(final int userid) throws SQLException {
					Object[] result = new Object[2];
					List<Task> tasks = new ArrayList<>();
					List<String> statusList = new ArrayList<>();
					final PreparedStatement ps = conn.prepareStatement(SELECT_TASK_SQL_QUERY);
					ps.setInt(1, userid);
					final ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						final int id = rs.getInt("ID");
						final int user_id = rs.getInt("USER_ID");
						final String title = rs.getString("TITLE");
						String desc = rs.getString("DESCRIPTION");
						if(desc.length()>50) {
							desc = desc.substring(0,50);
						}
						final int status_id = rs.getInt("STATUS_ID");
						final Date date = rs.getDate("DATE_UPDATED");
						final String status = rs.getString("NAME");
						//new java.util.Date(date.getTime()))
						//SimpleDateFormat formater = new SimpleDateFormat("dd/MM/YYYY");
						tasks.add(new Task(id,user_id,title,desc,status_id,date));
						statusList.add(status);
						
					}
					result[0] = tasks;
					result[1] = statusList;
					rs.close();
					ps.close();
					return result;
				}
				
				
				
				private static final String SELECT_ALL_STATUS_SQL_QUERY = 
						"SELECT * FROM STATUS";

				public List<Status> getStatus() throws SQLException {
					final List<Status> statuslist = new ArrayList<>();
					final Statement st = conn.createStatement();
					final ResultSet rs = st.executeQuery(SELECT_ALL_STATUS_SQL_QUERY);
					while (rs.next()) {
						final Integer id = rs.getInt("ID");
						final String name = rs.getString("NAME");
						statuslist.add(new Status(id, name));
					}
					rs.close();
					st.close();
					return statuslist;
				}
				
				private static final String UPDATE_TASK_QUERY = 
						"UPDATE TASK SET STATUS_ID = ? , DATE_UPDATED = now() WHERE ID = ?";

				public int updateTask(int statusId, int taskId) throws SQLException {
					final PreparedStatement ps = conn.prepareStatement(UPDATE_TASK_QUERY);
					ps.setInt(1, statusId);
					ps.setInt(2, taskId);
					final int rs = ps.executeUpdate();
					return rs;
				
				}
				
				private static final String FIND_USER = 
						"SELECT * FROM USER WHERE USERNAME=? AND USERPASS=?";

				public User findUser(String username, String password) throws SQLException {
					User user = null;
					final PreparedStatement ps = conn.prepareStatement(FIND_USER);
					ps.setString(1, username);
					ps.setString(2, password);
					final ResultSet rs = ps.executeQuery();
					while(rs.next()) {
					int id = rs.getInt("ID");
					int role_id = rs.getInt("ROLE_ID");
					user = new User(id,username,password,role_id);
					break;
					}
					return user;
				
				}
			
			public static void main(String[] args) throws Exception {
				
				System.out.println(" >> ProgramDB - Testing Place - START");
				System.out.println();
				
				final DbConnector db = DbConnector.getInstance();
				db.openDbConnection();
				
				// View Data
				final User r = db.findUser("u2","p2");
				System.out.println("View-Response: vehicleList.size(): " + r.getUsername());
				
				db.closeDbConnection();
				
				System.out.println();
				System.out.println(" >> ProgramDB - Testing Place - END");
			}	
}
