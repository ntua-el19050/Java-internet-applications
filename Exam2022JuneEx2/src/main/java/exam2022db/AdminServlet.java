package exam2022db;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final HttpSession session = request.getSession();
		final User sessionUser = (User) session.getAttribute("user");
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "index.html"); 
		} else {
			
			if(sessionUser.getRoleId()!=2) {
				response.getWriter().append("Page Not Accessible for Users");
			}
			
			else {
				
				List<Object> result = null;
				try {
					// Get ALL vehicles
					DbConnector.getInstance().openDbConnection();
					result = DbConnector.getInstance().adminCase();
					DbConnector.getInstance().closeDbConnection();
				} catch (Throwable t) {
					// Inform the user in case of an Error
					final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
					response.getWriter().append(errMsg);
					t.printStackTrace();
					return;
				}
				
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
	            out.println("<html><head>");
	            out.println("<title>Choose Page</title>\n");
	            out.println("</head><body>");
	            
	            out.println("<h1> " + sessionUser.getUsername() + " </h1>");
	            out.println("<h2>  Cases Table  </h2>");
	            out.println("<table>");
				out.println("<tr>");
				out.println("<th>Method</th>");
				out.println("<th>Count</th>");
				out.println("<tr>");
				
				for (int i =0; i< result.size();i++) {
					Object [] r =  (Object[]) result.get(i);
					out.println("<tr>");
					out.println("<td>"+ r[0] +"</td>");
					out.println("<td>"+ r[1] +"</td>");
					out.println("<tr>");
				}
				
				out.println("</table>");
				out.println("</body></html>");
				
				out.close();
				
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
