package uv.sokolovsky.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.SafeHtml;
import uv.sokolovsky.web.View;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_name", "address"}, name = "restaurants_unique_restaurant_name_address_idx")})
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "restaurant_name", nullable = false)
    @Size(min = 2, max = 120)
    @NotEmpty
    @SafeHtml(groups = {View.Web.class})
    private String restaurantName;

    @Column(name = "address", nullable = false)
    @Size(min = 5, max = 250)
    @NotEmpty
    @SafeHtml(groups = {View.Web.class})
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("description")
    @JsonIgnore
    protected List<Dish> dishes;

    public Restaurant() {}

    public Restaurant (String restaurantName, String address) {
        this(null, restaurantName, address);
    }

    public Restaurant(Integer id, String restaurantName, String address) {
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

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", restaurantName=" + restaurantName +
                ", address=" + address +
                '}';
    }
}
