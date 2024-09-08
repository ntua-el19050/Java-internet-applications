import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequests;
import javax.servlet.http.HttpServletResponse;


public class HelloCoolITHelp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException IOException {
                RequestDispatcher rd = request.getRequestDispatcher("hello.jsp");
                rd.forward(request, response);
            }    
}
