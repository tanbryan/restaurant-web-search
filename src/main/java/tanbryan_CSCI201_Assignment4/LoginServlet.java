package tanbryan_CSCI201_Assignment4;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
//import java.sql.*;
//import com.google.gson.Gson;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("loginUsername");
		String password = request.getParameter("loginPassword");
		pw.print(username);
		String email = request.getParameter("signupEmail");
		String user_id;
		
		
		int verification = JDBCConnector.loginUser(username, password);
		
		
		if(verification == 1) {
			user_id = JDBCConnector.findUserId(username);
			String userIdStr = String.valueOf(user_id);
			Cookie loginCookie = new Cookie("userLoggedIn", "true");
			Cookie useridCookie = new Cookie("userId", userIdStr);
			System.out.println(	useridCookie);
			response.addCookie(loginCookie);
			response.addCookie(useridCookie);
			response.sendRedirect("home.html");
			pw.print("Login successful");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            pw.print("Invalid username or password");
        }
		 pw.flush();
		
	}
    @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.sendRedirect("login.html?loginerror=true");
		}
}

