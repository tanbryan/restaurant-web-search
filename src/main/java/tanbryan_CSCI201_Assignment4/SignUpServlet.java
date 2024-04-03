package tanbryan_CSCI201_Assignment4;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/register")
public class SignUpServlet extends HttpServlet {
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter pw = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
				
			String username = request.getParameter("signupUsername");
			String password = request.getParameter("signupPassword");
			String email = request.getParameter("signupEmail");
			String confirm = request.getParameter("confirmPassword");
			int user_id;
			
			pw.print(username);
			
			
			
			int emailVerification = JDBCConnector.verifyUser(email);
			int usernameVerification = JDBCConnector.verifyUser(username);
			
			if(emailVerification == 1 || usernameVerification == 1) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
	            pw.print("Email or Username is registered");
			} 
			else {
				user_id = JDBCConnector.registerUser(username, password, email);
				String userIdStr = String.valueOf(user_id);
				Cookie loginCookie = new Cookie("userLoggedIn", "true");
				Cookie useridCookie = new Cookie("userId", userIdStr);
				System.out.println(	useridCookie);
				response.sendRedirect("home.html");
				pw.print("Register successful");
			}
			pw.flush();
			
			
		}    
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.sendRedirect("login.html");
		}
}
