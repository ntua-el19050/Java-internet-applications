package web2022;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class StoreDataServlet
 */
@WebServlet("/StoreDataServlet")
public class StoreDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		final HttpSession session = request.getSession();
		final Object[] sessionUser = (Object []) session.getAttribute("user");
		if(sessionUser==null) {
			response.getWriter().append("User is not logged in");
			return;
		}
		
		final String date = request.getParameter("datestr");
		final String  team1id = request.getParameter("team1id");
		final String team1score = request.getParameter("team1score");
		final String  team2id = request.getParameter("team2id");
		final String team2score = request.getParameter("team2score");
		int row =-1;
		
		final String dateFormat = "DD-MM-YYYY";
		final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		java.util.Date d =null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		
		try {
			final ExamDbConnector db = new  ExamDbConnector();
			db.openDbConnection();
			row = db.recordData(sqlDate, Integer.parseInt(team1id), Integer.parseInt(team1score), Integer.parseInt(team2id), Integer.parseInt(team2score));
			db.closeDbConnection();
						
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage();
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
		
		if(row==1) {
			List<Object> result = null;
			try {
				final ExamDbConnector db = new  ExamDbConnector();
				db.openDbConnection();
				result = db.fetchGames();
				db.closeDbConnection();
							
				
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Error ... " + t.getMessage();
				response.getWriter().append(errMsg);
				t.printStackTrace();
			}
			
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>All Games</title>\n");
            out.println("</head><body>");
            
            out.println("<h2>  Games Table  </h2>");
            out.println("<table>");
			out.println("<tr>");
			out.println("<th>Date</th>");
			out.println("<th>Team1</th>");
			out.println("<th>Team1Score</th>");
			out.println("<th>Team2</th>");
			out.println("<th>Team2Score</th>");
			out.println("<tr>");
			
			for (int i =0; i< result.size();i++) {
				Object [] r =  (Object[]) result.get(i);
				out.println("<tr>");
				out.println("<td>"+ r[0] +"</td>");
				out.println("<td>"+ r[1] +"</td>");
				out.println("<td>"+ r[2] +"</td>");
				out.println("<td>"+ r[3] +"</td>");
				out.println("<td>"+ r[4] +"</td>");
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
