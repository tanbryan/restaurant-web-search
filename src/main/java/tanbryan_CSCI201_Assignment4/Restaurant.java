package tanbryan_CSCI201_Assignment4;

public class Restaurant {
	private String name;
    private String address;
    private double rating;
    private String imageLink;
    private String yelpLink;
    private String price;
    private String cuisine;  
    private String phone; 
    private int restaurantid;

    public Restaurant(String name, String address, double rating, String imageLink, String yelpLink, String price, String cuisine, String phone) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.imageLink = imageLink;
        this.yelpLink = yelpLink;
        this.price = price;
        this.cuisine = cuisine;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String ImageLink) {
        this.imageLink = imageLink;
    }


    public String getYelpLink() {
        return yelpLink;
    }

    public void setYelpLink(String YelpLink) {
        this.yelpLink = yelpLink;
    }
    
    public String getPrice() {
        return price;
    }

    public void setPirce(String price) {
        this.price = price;
    }
    
    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    
    @Override
    public String toString() {
        return "Restaurant{" +
               "name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", rating=" + rating +
               ", imageLink='" + imageLink + '\'' +
               ", yelpLink='" + yelpLink + '\'' +
               ", price='" + price + '\'' +
               ", cuisine='" + cuisine + '\'' +
               ", phoneNumber='" + phone + '\'' +
               '}';
    }
}


