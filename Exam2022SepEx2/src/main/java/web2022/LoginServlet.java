package web2022;

import java.io.IOException;
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
		
		final String pass = Util.getHash256(password);
		// Check if user exists
		boolean exist = false;
		
		try {	
			final ExamDbConnector db = new ExamDbConnector();
			db.openDbConnection();
			exist = db.examineUser(username,pass);
			db.closeDbConnection();				
		}
		catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " No valid user !" + username + " " + password;
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
		if(!exist) {
			final String errMsg = "Error No such User!";
			response.getWriter().append(errMsg);
		}
		else {
			final HttpSession session = request.getSession();
			Object [] user = new Object[2];
			user[0] = username;
			user[1] = Util.getHash256(password);
			session.setAttribute("user", user);
			response.sendRedirect("InsertData.html");
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
