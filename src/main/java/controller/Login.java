package controller;

import java.io.IOException;

import dao.MyDao;
import dto.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//Mapping the url
@WebServlet("/login")

public class Login extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     //receive values from front end
	String email=req.getParameter("email");
	String password=req.getParameter("pass");
	
	//verify if email exists
	MyDao m=new MyDao();
	Customer c=m.fetchByEmail(email);
	if(c==null)
	{
		resp.getWriter().print("<h1 style='color:red'>Invalid Email</h1>");
		req.getRequestDispatcher("Login.html").include(req, resp);
	}
	else
	{
		if(password.equals(c.getPass()))
		{
			resp.getWriter().print("<h1 style='color:green'>Login sucess</h1>");
			req.getRequestDispatcher("CustomerHome.html").include(req, resp);
		}
		else {
		
			resp.getWriter().print("<h1 style='color:red'>Invalid password</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		}
	}
}
}