package tanbryan_CSCI201_Assignment4;

import java.net.URLEncoder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class YelpAPIParser {
    private static final String API_KEY = "CoTQjw0LmUQrbVKnBS3p1rhFUqeSkcFOfAxgxRd2pr0hcSBO5m5SpgWe70lf7uYlFjF4WxKpQMcQzRGX8d-SKIpQtUpaCORRmLKIP7zxnQxjmsIl6nJr-9Ms9D5AZXYx";

    public static List<Restaurant> getRestaurantData(double latitude, double longitude, String restaurantName, String criteria) {

        try {
            // Encode the restaurantName to handle special characters
            String encodedName = URLEncoder.encode(restaurantName, "UTF-8");

            // Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.yelp.com/v3/businesses/search?latitude=" + latitude + "&longitude=" + longitude + "&term=" + encodedName + "&sort_by=" + criteria))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String jsonData = response.body();

            // Process the JSON data
            if (jsonData != null && !jsonData.isEmpty()) {
                List<Restaurant> restaurantList = new ArrayList<>();
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(jsonData, JsonObject.class);
                JsonArray businesses = jsonResponse.getAsJsonArray("businesses");

                for (JsonElement businessElement : businesses) {
                    JsonObject business = businessElement.getAsJsonObject();

                    String name = business.get("name").getAsString();
                    String image_url = business.get("image_url").getAsString();
                    String url = business.get("url").getAsString().split("\\?")[0];
                    double rating = business.get("rating").getAsDouble();
                    String price = business.has("price") ? business.get("price").getAsString() : "";
                    String displayPhone = business.has("display_phone") ? business.get("display_phone").getAsString() : "No Display Phone";
                    JsonArray categories = business.getAsJsonArray("categories");
                    String cuisine = categories.size() > 0 ? categories.get(0).getAsJsonObject().get("title").getAsString() : "";

                    JsonObject location = business.getAsJsonObject("location");
                    JsonArray display_address = location.getAsJsonArray("display_address");
                    String address = String.join(", ", gson.fromJson(display_address, String[].class));

                    restaurantList.add(new Restaurant(name, address, rating, image_url, url, price, cuisine, displayPhone));
                }
                return restaurantList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); 
    }
//    public static void main(String[] args) {
//        try {
//            // Sample input values for testing
//            double latitude = 34.0522; // Example latitude, e.g., Los Angeles
//            double longitude = -118.2437; // Example longitude
//            String restaurantName = "ramen"; // Example search term
//            String criteria = "review_count"; // Example sorting criteria
//
//            // Call the getRestaurantData function
//            List<Restaurant> restaurants = YelpAPIParser.getRestaurantData(latitude, longitude, restaurantName, criteria);
//
//            // Print the results
//            System.out.println("Restaurants found: ");
//            for (Restaurant restaurant : restaurants) {
//                System.out.println("Name: " + restaurant.getName());
//                System.out.println("Address: " + restaurant.getAddress());
//                System.out.println("Rating: " + restaurant.getRating());
//                // ... Print other restaurant details as needed ...
//                System.out.println("-------------------------------------------------");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}


