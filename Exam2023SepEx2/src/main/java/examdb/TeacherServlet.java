package examdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		if(username==null || password.trim().equals("")) {
			response.getWriter().append("Invalid Credentials");
			return;
		}
		
		List<Object> objList = null;
		
			
			
			try {
				final DbConn db = DbConn.getInstance();
				db.openDbConnection();
				try {
					objList = db.getTeacherCoursesStudents(username,password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				db.closeDbConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
			final Map<String, Integer> map = new HashMap<>();
			for (int i = 0; i < objList.size(); i++) {
				Object [] obj =  (Object[]) objList.get(i);
				// Character of the given String at position i
				String c = (String)obj[1];
				
				// Check and Update Map
				if (!map.containsKey(c)) {
					map.put(c, (int)obj[0]);
				}
			}
			
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<title>Teacher Courses</title>\n");
	        out.println("</head><body>");
	        
	        out.println("<h1> Courses </h1>" );
	        
	        if (objList != null) {
				final List<Entry<String,Integer>> entryList = new ArrayList<>(map.entrySet());
				for (Entry<String, Integer> entry : entryList) {
					String c = entry.getKey();
					Integer id = entry.getValue();
					out.println("<h2> Course name:" + c + " Course Id: " + id + "</h2>" );
					out.println("<table>");
					out.println("<tr>");
					out.println("<th>Student Id</th>");
					out.println("<th>Grade</th>");
					out.println("</tr>");
					for (int i =0; i< objList.size();i++) {
						Object [] obj =  (Object[]) objList.get(i);
						
						if(c.equals(obj[1])) {
							out.println("<tr>");
							out.println("<td>"+ obj[3] + "</td>");
							out.println("<td>"+ obj[4] + "</td>");
							out.println("</tr>");
						}	
						
					}
					out.println("</table>");
					
				}
				
				out.println("</body></html>");
				
				out.close();
	        }
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
