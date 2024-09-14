<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="exam2023db.*"%>
<%@page import="java.util.List"%> 
<%@page import="java.io.PrintWriter"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Task Status</title>


</head>
<body>

<%
	final User sessionUser = (User) session.getAttribute("user");
	if (sessionUser == null ) {
		// Redirect User to Login Page
		response.sendRedirect( "index.html"); 
	}
	%>	
	
<%
	final String status_id = request.getParameter("status_id");
	final String task_id = request.getParameter("task_id");
	
	List<Status> statuslist = null;
	try {
		final DbConnector db = DbConnector.getInstance();
		db.openDbConnection();
		statuslist = db.getStatus();
		db.closeDbConnection();
					
		
	} catch (Throwable t) {
		// Inform the user in case of an Error
		final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
		response.getWriter().append(errMsg);
		t.printStackTrace();
	}
	%>

	<h1>SELECT STATUS </h1>
	<form action="UpdateTaskServlet" method = "POST">
	<input type="hidden" name = "task_id" value= <%= task_id%> >
	<input type="hidden" name = "oldstatus" value= <%= status_id%> >
	<select name="newstatus" id = "statusid">
	
	<%
	
	//response.setContentType("text/html; charset=UTF-8");
	//final PrintWriter resp = response.getWriter();
	
	for (Status s : statuslist){
		if(s.getId()== Integer.parseInt(status_id)){
	%>		
		<option value= <%= s.getId() %> selected> <%= s.getName() %> </option>
	<%	
		}
		else{
	%>
		<option value= <%= s.getId() %>> <%= s.getName() %> </option>
	<% 		
		}
	}	
%>	
	
	</select>
	
	<input type="submit" value="Change Status" >
	</form>

	<p>
		<a href="LogoutServlet">Logout</a>
	</p>
	
</body>
</html>