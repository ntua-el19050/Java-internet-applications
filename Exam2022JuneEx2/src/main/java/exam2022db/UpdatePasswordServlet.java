package exam2022db;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class UpdatePasswordServlet
 */
@WebServlet("/UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("oldPassword");
		final String password2 = request.getParameter("newPassword");
		
		
		// Check Given Data 
		if (password == null || password.trim().equals("") || password2.trim().equals("") ) {
			// Inform the user about the problem
			response.getWriter().append("Invalid Data - Password #1 and #2 are different ! No user account created !");
			return;
		} 
		
		User user = null;
		
		try {
			final DbConnector db = DbConnector.getInstance();
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
			//response.sendRedirect( "index.html");
		}
		else {
			try {
				int row = 0;
				final DbConnector db = DbConnector.getInstance();
				db.openDbConnection();
				row = db.UpdateUser(username,Util.getHash256(password2));
				db.closeDbConnection();
				if(row==0) {
					response.getWriter().append("0 Rows Affected, No Update");
				}
				else {
					response.sendRedirect( "index.html");
				}
							
				
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
