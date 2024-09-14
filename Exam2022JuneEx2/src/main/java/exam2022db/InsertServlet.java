package exam2022db;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
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
			
			if(sessionUser.getRoleId()!=1) {
				response.getWriter().append("Page Not Accessible for Admins");
			}
			
			else {
				
				int uid = sessionUser.getId();
				final String dateFormat = "yyyy/MM/dd";
				final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				Date date = null;
				try {
					date = sdf.parse(request.getParameter("dDate"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int mid = Integer.parseInt(request.getParameter("Method"));
				String loc = null;
				String rid = null;
				if(mid==2) {
					loc = request.getParameter("location");
				}
				if(mid==3) {
					rid = request.getParameter("repID");
				}
				
				int result = 0;
				try {
					
					// Get ALL vehicles
					DbConnector.getInstance().openDbConnection();
					result = DbConnector.getInstance().insertCase(uid,date,mid,loc,rid);
					DbConnector.getInstance().closeDbConnection();
				} catch (Throwable t) {
					// Inform the user in case of an Error
					final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
					response.getWriter().append(errMsg);
					t.printStackTrace();
					return;
				}
				
				response.getWriter().append("Successful Insertion");
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
