package com.singInUP.servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationSrv")
public class RegistrationSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("password");
		String umobile = request.getParameter("contact");
		
		
		Connection con =null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/SignInSignUpProj?useSSL=false";
			String user ="root";
			String pas = "root";
			con = DriverManager.getConnection(url,user,pas);
			
			String query = "insert into userDetais(uname,upswd,uemai,umobile) values(?,?,?,?)";
			PreparedStatement psmt  = con.prepareStatement(query);
			psmt.setString(1, uname);
			psmt.setString(2, upwd);
			psmt.setString(3,uemail);
			psmt.setString(4, umobile);
			
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("registration.jsp");
			
			int rowCount = psmt.executeUpdate();
			if(rowCount>0) {
				request.setAttribute("status", "success");
			}
			else{
				request.setAttribute("status", "failed");
			}
			
			rd.forward(request, response);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
