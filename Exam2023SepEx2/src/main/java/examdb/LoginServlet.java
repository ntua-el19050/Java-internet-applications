package examdb;

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
		
//		final String username = request.getParameter("username");
//		final String password = request.getParameter("password");
//		
//		User user = null;
//		try {
//			final DbConnector db = DbConnector.getInstance();
//			db.openDbConnection();
//			user = db.findUser(username, password);
//			db.closeDbConnection();
//						
//			
//		} catch (Throwable t) {
//			// Inform the user in case of an Error
//			final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
//			response.getWriter().append(errMsg);
//			t.printStackTrace();
//		}
//		
//		if(user!=null) {
//			
//			final HttpSession session = request.getSession();
//			session.setAttribute("user", user);
//			response.sendRedirect( "UpdateTask.jsp");
//		}
//		else {
//			final String errMsg = "Invalid Credentials";
//			response.getWriter().append(errMsg);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
