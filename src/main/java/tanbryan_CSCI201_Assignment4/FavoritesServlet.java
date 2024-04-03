package tanbryan_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(urlPatterns = {"/addFav", "/deleteFav", "/displayFav"})
public class FavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/addFav":
                addFavorites(request, response);
                break;
            case "/deleteFav":
                deleteFavorite(request, response);
                break;
            default:
                // Handle unknown requests
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void addFavorites(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resName = request.getParameter("Restaurant");
        String Id = request.getParameter("Name");
        int userId = Integer.parseInt(Id);

        if (resName != null && userId != -1) {
            int resId = JDBCConnector.findResId(resName);
            JDBCConnector.addFavorites(userId, resId);
            int isFav = JDBCConnector.verifyFav(resId);
            String encodedResName = URLEncoder.encode(resName, StandardCharsets.UTF_8.toString());

            if (isFav == 1) {
                Cookie favCookie = new Cookie("fav_" + encodedResName, "true");
                response.addCookie(favCookie);
                System.out.println("Favorite cookie added for: " + encodedResName);
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson("Favorite added successfully."));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson("Restaurant ID or Username is missing."));
        }
    }

   
    

    private void deleteFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String resName = request.getParameter("Restaurant");
        String Id = request.getParameter("Name");
        int userId = Integer.parseInt(Id);
        
        if (resName != null && userId != -1) {
            int resId = JDBCConnector.findResId(resName);
            JDBCConnector.deleteFavorites(userId, resId);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson("Favorite deleted successfully."));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson("Restaurant ID or Username is missing."));
        }
    }

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/displayFav".equals(path)) {
            displayFavorites(request, response);
        } else {
            // Redirect or handle other GET requests
            response.sendRedirect("favorites.html?loginerror=true");
        }
    }
    private void displayFavorites(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("Name");
        int userId = Integer.parseInt(idParam);

        System.out.println("Fetching favorites for user ID: " + userId); // Debugging line

        List<Restaurant> favRes = JDBCConnector.getFavorite(userId);
        String username = JDBCConnector.findUserName(idParam);

        System.out.println("Fetched " + favRes.size() + " favorites for username: " + username); // Debugging line

        Gson gson = new Gson();
        String favJson = gson.toJson(favRes);
        String jsonResponse = "{\"username\": \"" + username + "\", \"favorites\": " + favJson + "}";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();

        System.out.println("Sent favorites data to client"); // Debugging line
    }
}

