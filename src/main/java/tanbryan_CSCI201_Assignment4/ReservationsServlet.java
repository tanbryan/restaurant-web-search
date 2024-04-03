package tanbryan_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tanbryan_CSCI201_Assignment4.JDBCConnector.RestaurantReservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@WebServlet(urlPatterns = {"/addRes", "/displayRes"})
public class ReservationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String path = request.getServletPath();

         if ("/addRes".equals(path)) {
        	 addReservations(request, response);
         } else {
             response.sendRedirect("reservations.html?loginerror=true");
         }
     }

    private void addReservations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resName = request.getParameter("Restaurant");
        String Id = request.getParameter("Name");
        int userId = Integer.parseInt(Id);
        String date = request.getParameter("Date");
        String time = request.getParameter("Time");

        if (resName != null && userId != -1) {
            int resId = JDBCConnector.findResId(resName);
            JDBCConnector.addReservations(userId, resId, date, time);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson("Reservations added successfully."));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson("Restaurant ID or Username is missing."));
        }
    }

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/displayRes".equals(path)) {
        	displayReservations(request, response);
        } else {
            // Redirect or handle other GET requests
            response.sendRedirect("reservations.html?loginerror=true");
        }
    }
    private void displayReservations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("Name");
        int userId = Integer.parseInt(idParam);

        System.out.println("Fetching Reservations for user ID: " + userId); // Debugging line

        List<RestaurantReservation> resRes = JDBCConnector.getReservations(userId);
        String username = JDBCConnector.findUserName(idParam);


        Gson gson = new Gson();
        String resJson = gson.toJson(resRes);
        String jsonResponse = "{\"username\": \"" + username + "\", \"reservations\": " + resJson + "}";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();

        System.out.println("Sent Reservations data to client"); // Debugging line
    }
}

