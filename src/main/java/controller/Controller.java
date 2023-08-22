package controller;

import java.io.IOException;

import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.MyDao;
import dto.Customer;

//This is to map the action URL to this class(Shold be same as action-Case sensitive)
@WebServlet("/signup")
//to receive image we need to use this-enctype
@MultipartConfig
//This is to make class as Servlet class
public class Controller extends HttpServlet

{
	@Override
//When there is form and we want data to be secured so doPost
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Logic to receive values from front end
		String name = req.getParameter("name");
		String Password = req.getParameter("pass");
		long mobile = Long.parseLong(req.getParameter("mob"));
		String email = req.getParameter("email");
		String country = req.getParameter("coun");
		String gender = req.getParameter("gender");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));

		Part picture = req.getPart("picture");
		byte[] picture1 = null;
		picture1 = new byte[picture.getInputStream().available()];
		picture.getInputStream().read(picture1);
		int age = Period.between(dob, LocalDate.now()).getDays();
		System.out.println(name);
		System.out.println(Password);
		System.out.println(mobile);
		System.out.println(email);
		System.out.println(country);
		System.out.println(dob);
		System.out.println(gender);
		MyDao m1 = new MyDao();
		// Logic to verify email and mobile number is not reapeted
		if (m1.fetchByEmail(email) == null && m1.FetchByMobile(mobile) == null) {
			// Loading the values inside the object
			Customer c = new Customer();
			c.setCountry(country);
			c.setDob(dob);
			c.setEmail(email);
			c.setGender(gender);
			c.setAge(age);	
			c.setMobile(mobile);
			c.setName(name);
			c.setPass(Password);
			c.setPicture(picture1);

			// persisting

			m1.save(c);
			resp.getWriter().print("<h1 style='color:green'>Account Create Successfully</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			resp.getWriter().print("<h1 style='color:red'>Mobile and email should be unique</h1>");
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
	}
}