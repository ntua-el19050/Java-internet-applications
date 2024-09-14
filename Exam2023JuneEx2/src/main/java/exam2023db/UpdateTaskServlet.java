package exam2023db;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class UpdateTaskServlet
 */
@WebServlet("/UpdateTaskServlet")
public class UpdateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String oldstatus = request.getParameter("oldstatus");
		final String newstatus = request.getParameter("newstatus");
		
		int rows = 0;
		if(!oldstatus.equals(newstatus)) {
		try {
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			rows = db.updateTask(Integer.parseInt(request.getParameter("newstatus")), Integer.parseInt(request.getParameter("task_id")));
			db.closeDbConnection();
						
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		}
		response.setContentType("text/html; charset=UTF-8");
		final PrintWriter resp = response.getWriter();
		resp.println("<h1> Database Updated, "+ rows +" rows affected </h1>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
