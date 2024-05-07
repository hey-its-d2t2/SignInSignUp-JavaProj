package com.singInUP.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uemail = request.getParameter("username");
		String upass = request.getParameter("password");
		HttpSession session = request.getSession();

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/SignInSignUpProj?useSSL=false";
			String user = "root";
			String pas = "root";
			con = DriverManager.getConnection(url, user, pas);

			String query = "select * from userDetais where uemai=? and upswd=?";
			PreparedStatement psmt = con.prepareStatement(query);
			psmt.setString(1, uemail);
			psmt.setString(2, upass);

			ResultSet rs = psmt.executeQuery();
			
			RequestDispatcher rd = null;
			
			
			if (rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				rd = request.getRequestDispatcher("index.jsp");

			} else {
				session.setAttribute("status", "failed");
				rd = request.getRequestDispatcher("login.jsp");
			}

			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
