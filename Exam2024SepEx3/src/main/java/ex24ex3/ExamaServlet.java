package ex24ex3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ex24ex3.ExamDbConnector;


@WebServlet("/ExamaServlet")
public class ExamaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String characteristic = request.getParameter("characteristics");
		final String equation = request.getParameter("equation");
		List<Object> results = new ArrayList<>();
		
		try {	
			final ExamDbConnector db = new ExamDbConnector();
			db.openDbConnection();
			results = db.fetchTasks(characteristic,equation);
			db.closeDbConnection();				
		}
		catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " No valid sentece !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
		if(results == null) {
			final String errMsg = "Error No such sentence!";
			response.getWriter().append(errMsg);
		}
		else {
			
			
				
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>All Tasks</title>\n");
            out.println("<style>");
            out.println("h2{color: blue;}");
            out.println("</style>");
            out.println("</head><body>");
            
            out.println("<h2><b>  Tasks Table  </b></h2>");
            out.println("<table>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>TEXTSTR</th>");
			out.println("<th>CHARS</th>");
			out.println("<th>LETTERS</th>");
			out.println("<th>NUMBERS</th>");
			out.println("<th>WORDS</th>");
			out.println("<tr>");
			
			for (int i = 0; i < results.size(); i++) {
			    Object[] r = (Object[]) results.get(i);
			   

			    out.println("<tr>");
			    out.println("<td>" + r[0] + "</td>");
			    out.println("<td>" + r[1] + "</td>");
				out.println("<td>"+ r[2] +"</td>");
				out.println("<td>"+ r[3] +"</td>");
				out.println("<td>" + r[4] + "</td>");
				out.println("<td>" + r[5] + "</td>");
				out.println("<tr>");
			}
			
			out.println("</table>");
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