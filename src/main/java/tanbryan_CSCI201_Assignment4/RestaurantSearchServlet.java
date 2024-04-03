package tanbryan_CSCI201_Assignment4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/search")
public class RestaurantSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
	        String restaurantName = request.getParameter("name");
	        String latitudeParam = request.getParameter("latitude");
	        String longitudeParam = request.getParameter("longitude");
	        String criteria = request.getParameter("criteria");
	
	        double latitude = latitudeParam != null ? Double.parseDouble(latitudeParam) : 0.0;
	        double longitude = longitudeParam != null ? Double.parseDouble(longitudeParam) : 0.0;
	
	        List<Restaurant> restaurants = YelpAPIParser.getRestaurantData(latitude, longitude, restaurantName, criteria);
	
	        for (Restaurant restaurant : restaurants) {
	            JDBCConnector.storeRestaurant(
	                restaurant.getName(),
	                restaurant.getAddress(),
	                restaurant.getPhone(),
	                restaurant.getRating(),
	                restaurant.getImageLink(),
	                restaurant.getYelpLink(),
	                restaurant.getPrice(),
	                restaurant.getCuisine()
	            );
	        }
	
	        Gson gson = new Gson();
	        String jsonResponse = gson.toJson(restaurants);
	     
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	        out.print(jsonResponse);
	        out.flush();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}

