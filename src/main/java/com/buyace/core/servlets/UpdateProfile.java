package com.buyace.core.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.buyace.core.beans.Customer;
import com.buyace.core.beans.Product;

public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("name");
		String userMail = request.getParameter("email");
		long mobile = Long.parseLong(request.getParameter("mobile"));
		int userid = Integer.parseInt(request.getParameter("userId"));
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		Customer customer = new Customer(userName, userMail, mobile, password, gender);
		customer.setUserid(userid);
		Session session = com.buyace.core.hibernate.util.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(customer);
		session.getTransaction().commit();
		
		response.getWriter().print("<script>alert('Profile Updated.!');</script>");
		request.getSession().removeAttribute("customer");
		response.sendRedirect("userLogin.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}