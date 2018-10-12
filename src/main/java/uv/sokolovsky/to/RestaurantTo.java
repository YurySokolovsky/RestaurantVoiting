package uv.sokolovsky.to;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RestaurantTo extends BaseTo {

    @NotEmpty
    @Size(min = 2, max = 120, message = "name must between 2 and 120 characters")
    @SafeHtml
    private String restaurantName;

    @NotEmpty
    @Size(min = 5, max = 250, message = "address must between 5 and 120 characters")
    @SafeHtml
    private String address;

    public RestaurantTo () {}

    public RestaurantTo (Integer id, String restaurantName, String address) {
        super(id);
        this.restaurantName = restaurantName;
        this.address = address;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantТо{" +
                "id=" + id +
                ", restaurantName=" + restaurantName + '\'' +
                ", address=" + address +
                '}';
    }
}
