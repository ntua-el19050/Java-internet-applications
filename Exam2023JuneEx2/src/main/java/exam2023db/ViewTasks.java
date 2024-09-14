package exam2023db;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;


/**
 * Servlet implementation class ViewTasks
 */
@WebServlet("/ViewTasks")
public class ViewTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTasks() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		if(username==null || password.trim().equals("")) {
			response.getWriter().append("Invalid Credentials");
			return;
		}
		
		Object[] userRole;
		String n = "";
		int i = 0;
		int userid=0;
		try {
			
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			userRole = db.getRole(username,password);
			db.closeDbConnection();
			n =  ((Role) userRole[0]).getName();
			i = ((Role) userRole[0]).getId();
			userid = ((int) userRole[1]);
						
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
		if(n=="ADMINISTRATOR") {
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<title>Roles</title>\n");
	        out.println("</head><body>");
	        
	        out.println("<h1> The Role of the user is: " + n + "</h1>" );
	        out.println("</body></html>");
			
			out.close();
		}
		else {
			
			Object [] res;
			//List<Task> tasks;
			try {
				
				final DbConnector db = DbConnector.getInstance();
				db.openDbConnection();
				res = db.getTask(userid);
				db.closeDbConnection();
				
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
		        out.println("<html><head>");
		        out.println("<title>Roles</title>\n");
		        out.println("</head><body>");
		        
		        out.println("<h1> The Role of the user is: " + n + "</h1>" );
		        out.println("<table>");
		        int j = 0;
		        List<String> status = (List<String>) res[1];
		        
		        for(Task t : (List<Task>)res[0]){
		        	
		        	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/YYYY");
					out.println("<tr>");
					
					out.println("<td>" + t.getId() + "</td>");
					out.println("<td>" + t.getTitle() + "</td>");
					out.println("<td>" + t.getDescription() + "</td>");
					out.println("<td>" + t.getStatusId() + "</td>");
					out.println("<td>" + formater.format(t.getDateUpdate()) + "</td>");
					out.println("<td>" + status.get(j)+ "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body></html>");
				
				out.close();
				
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
			}
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
