package tanbryan_CSCI201_Assignment4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;

public class JDBCConnector {
	// use GPT 4 generate this function
	public static int registerUser(String username, String password, String email) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int user_id = 0;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			if(conn!=null) {
				System.out.println("Connection established.");
			}
			ps = conn.prepareStatement("INSERT INTO Users(username, password, email) VALUES(?, ?, ?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully.");
            } else {
                System.out.println("Failed to insert the record.");
            }
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs1 != null) {
					rs1.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (ps1 != null) {
					ps1.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return user_id;
	}
	//adapt the gpt generated complete the rest
	public static int verifyUser(String email) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int verify = 0;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			ps = conn.prepareStatement("SELECT * FROM Users WHERE email = ?");
			ps.setString(1, email);

			rs = ps.executeQuery();
			if(rs.next())
				verify = 1;
			else
				verify = 0;
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return verify;
	}
	
	public static int loginUser(String username, String password) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			ps = conn.prepareStatement("SELECT user_id from Users where username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = 1;
			}
			else {
				result = 0;
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return result;
	}
	
	public static void storeRestaurant(String name, String address, String phone, Double rating, String imageUrl, String yelpUrl, String price, String cuisine) {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");

	        String query = "INSERT INTO Restaurants (name, address, phone, rating, image_url, yelp_url, price, cuisine) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        ps = conn.prepareStatement(query);


	        ps.setString(1, name);
	        ps.setString(2, address);
	        ps.setString(3, phone);
	        ps.setDouble(4, rating);
	        ps.setString(5, imageUrl);
	        ps.setString(6, yelpUrl);
	        ps.setString(7, price);
	        ps.setString(8, cuisine);


	        ps.executeUpdate();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException sqle) {
	        System.out.println("SQLException in storeRestaurant: " + sqle.getMessage());
	        sqle.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException while closing resources: " + sqle.getMessage());
	        }
	    }
	}
	
	public static void addFavorites(int user_id, int restaurant_id) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			ps = conn.prepareStatement("INSERT INTO Favorites(user_id, restaurant_id) VALUES(?, ?) ");
			ps.setInt(1, user_id);
			ps.setInt(2, restaurant_id);
			int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("added favorites..");
            } else {
                System.out.println("Not add into favoritesss");
            }
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static int verifyFav(int resId) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int verify = 0;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			ps = conn.prepareStatement("SELECT * FROM Favorites WHERE restaurant_id = ?");
			ps.setInt(1, resId);

			rs = ps.executeQuery();
			if(rs.next())
				verify = 1;
			else
				verify = 0;
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return verify;
	}
	
	public static void deleteFavorites(int user_id, int restaurant_id) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			System.out.println("In addFavorites.");
			ps = conn.prepareStatement("DELETE FROM Favorites WHERE user_id = ? AND restaurant_id = ? ");
			ps.setInt(1, user_id);
			ps.setInt(2, restaurant_id);
			int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("deleted favorites..");
            } else {
                System.out.println("Not deleted into favorites");
            }
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static List<Restaurant> getFavorite(int userId) {
	    List<Restaurant> favorites = new ArrayList<>();
	    String query = "SELECT r.name, r.address, r.rating, r.image_url, r.yelp_url, r.price, r.cuisine, r.phone " +
	                   "FROM Restaurants r JOIN Favorites f ON r.restaurant_id = f.restaurant_id " +
	                   "WHERE f.user_id = ?";

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, userId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Restaurant restaurant = new Restaurant(
	                        rs.getString("name"),
	                        rs.getString("address"),
	                        rs.getDouble("rating"),
	                        rs.getString("image_url"),
	                        rs.getString("yelp_url"),
	                        rs.getString("price"),
	                        rs.getString("cuisine"),
	                        rs.getString("phone")
	                    );
	                    favorites.add(restaurant);
	                }
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("SQLException in getFavorite: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return favorites;
	}


	
	public static int findResId(String resName) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resId= -1;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
		  
			ps = conn.prepareStatement("SELECT restaurant_id FROM Restaurants WHERE name = ?");
			ps.setString(1, resName);
			
			rs = ps.executeQuery();
			if(rs.next()){
				resId = rs.getInt(1);
			}
			else {
				resId=-1;
			}
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return resId;
	}
	public static String findUserName(String userId) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userName= null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
		  
			ps = conn.prepareStatement("SELECT username FROM Users WHERE user_id = ?");
			ps.setString(1, userId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				userName = rs.getString(1);
			}
			else {
				userName=null;
			}
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return userName;
	}
	public static String findUserId(String username) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userId= null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
		  
			ps = conn.prepareStatement("SELECT user_id FROM Users WHERE username = ?");
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			if(rs.next()){
				userId = rs.getString(1);
			}
			else {
				userId=null;
			}
			
	      
		} catch (SQLException sqle) {
			System.out.println ("SQLException in registerUser: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return userId;
	}
	
	public static void addReservations(int user_id, int restaurant_id, String date, String time) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
			ps = conn.prepareStatement("INSERT INTO Reservations(user_id, restaurant_id,reservation_date,reservation_time ) VALUES(?, ?,?,?) ");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		    java.util.Date parsedDate = dateFormat.parse(date);
		    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

		        // Convert String time to java.sql.Time
		    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); 
		    long ms = timeFormat.parse(time).getTime();
		    java.sql.Time sqlTime = new java.sql.Time(ms);
		        
			ps.setInt(1, user_id);
			ps.setInt(2, restaurant_id);
			ps.setDate(3, sqlDate);
			ps.setTime(4, sqlTime);
			
			int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("added reservations..");
            } else {
                System.out.println("Not add into reservations");
            }
			
	      
		}catch (ParseException e) {
	        e.printStackTrace();
	        // Handle parsing errors here
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	
	public static class RestaurantReservation {
	    private Restaurant restaurant;
	    private Date reservationDate;
	    private Time reservationTime;

	    public RestaurantReservation(Restaurant restaurant, Date reservationDate, Time reservationTime) {
	        this.restaurant = restaurant;
	        this.reservationDate = reservationDate;
	        this.reservationTime = reservationTime;
	    }

	    public Restaurant getRestaurant() {
	        return restaurant;
	    }

	    public Date getReservationDate() {
	        return reservationDate;
	    }

	    public Time getReservationTime() {
	        return reservationTime;
	    }

	    public void setRestaurant(Restaurant restaurant) {
	        this.restaurant = restaurant;
	    }

	    public void setReservationDate(Date reservationDate) {
	        this.reservationDate = reservationDate;
	    }

	    public void setReservationTime(Time reservationTime) {
	        this.reservationTime = reservationTime;
	    }
	    @Override
	    public String toString() {
	        return "RestaurantReservation{" +
	               "restaurantName='" + restaurant + '\'' +
	               ", reservationDate='" + reservationDate + '\'' +
	               ", reservationTime='" + reservationTime + '\'' +
	               '}';
	    }
	}

	public static List<RestaurantReservation> getReservations(int userId) {
		List<RestaurantReservation> reservations = new ArrayList<>();
	    String query = "SELECT r.name, r.address, r.rating, r.image_url, r.yelp_url, r.price, r.cuisine, r.phone, g.reservation_date, g.reservation_time " +
	               "FROM Restaurants r JOIN Reservations g ON r.restaurant_id = g.restaurant_id " +
	               "WHERE g.user_id = ?";


	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/joestable?user=root&password=tsk20020702");
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, userId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Restaurant restaurant = new Restaurant(
	                        rs.getString("name"),
	                        rs.getString("address"),
	                        rs.getDouble("rating"),
	                        rs.getString("image_url"),
	                        rs.getString("yelp_url"),
	                        rs.getString("price"),
	                        rs.getString("cuisine"),
	                        rs.getString("phone")
	                    );
	                    Date reservationDate = rs.getDate("reservation_date");
	                    Time reservationTime = rs.getTime("reservation_time");

	                    RestaurantReservation restaurantReservation = new RestaurantReservation(restaurant, reservationDate, reservationTime);
	                    reservations.add(restaurantReservation);
	                }
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("SQLException in getFavorite: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return reservations;
	}
//	public static void main(String[] args) {
//	    int testUserId = 1; // Example test user ID
//	    List<RestaurantReservation> reservations = getReservations(testUserId);
//
//	    for (RestaurantReservation res : reservations) {
//	        System.out.println(res);
//	    }
//	}
}