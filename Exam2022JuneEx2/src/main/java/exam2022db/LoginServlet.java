package exam2022db;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		if (username == null || password.trim().equals("")) {
			// Inform the user about the problem
			response.getWriter().append("Invalid Data ! ");
			return;
		}
		
		
		// Check if user exists
		User user = null;
		
		try {
			final DbConnector db = DbConnector.getInstance();
			System.out.print("hey");
			db.openDbConnection();
			user = db.findUser(username,Util.getHash256(password));
			db.closeDbConnection();
						
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " No valid user !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
		if(user==null) {
			final String errMsg = "Error No such User!";
			response.getWriter().append(errMsg);
		}
		else {
			final HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>Choose Page</title>\n");
            out.println("</head><body>");
            
            out.println("<p> " + username + " </p>");  
            out.println( "<p><a href=\"AdminServlet\">Admin Page</a> </p>");
            out.println( "<p><a href=\"UserServlet\">User Page</a> </p>");
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
